package cn.gq.eshop.car.service.impl;

import cn.gq.eshop.car.service.ICartService;
import cn.gq.eshop.mapper.TbItemMapper;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import cn.gq.util.jides.JedisClientPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
     @Autowired
     JedisClientPool jedisClientPool ;
     @Autowired
    TbItemMapper tbItemMapper ;
    @Override
     public EShopResult addCart(long userId,long itemId,int num) {
        //在controller当中判断用户是否登录
        //redis当中判断是否存在这个用户的基本购物信息
        Boolean hexists = jedisClientPool.hexists("CART:" + userId, itemId + "");
        if(hexists){
            String  json = jedisClientPool.hget("CART:" + userId, itemId + "");
            TbItem tbItem =  JsonUtils.jsonToPojo(json, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            jedisClientPool.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
            return  EShopResult.ok();
        }
        //如果不存在则将当前的商品信息保存到redis当中
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        String images = item.getImage();
        if(StringUtils.isNotBlank(images)){
            item.setImage(item.getImage().split(",")[0]);
        }

        item.setNum(num);
        jedisClientPool.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(item));
        return EShopResult.ok();
    }
      /**
       * 用来真整合服务端和cookie当中的商品信息
       * */
    @Override
    public EShopResult mergeCart(long userId, List<TbItem> list) {
         for(TbItem item : list){
          addCart(userId,item.getId(),item.getNum());
         }
        return EShopResult.ok();
    }

    @Override
    public List<TbItem> getCatList(long userId) {
         List<TbItem> list = new ArrayList<>() ;
        List<String> hvals = jedisClientPool.hvals("CART:" + userId);
         for (String json : hvals){
             TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
             list.add(item);
         }
        return list;
    }

    @Override
    public EShopResult updateCart(long userId,long itemId,int num) {
        String json = jedisClientPool.hget("CART:" + userId, itemId + "");
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
        jedisClientPool.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(item));
        return EShopResult.ok();
    }

    @Override
    public EShopResult deleteCart(long userId ,long itemId) {
        jedisClientPool.hdel("CART:" + userId, itemId + "");
        return EShopResult.ok();
    }
}
