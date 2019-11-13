package cn.gq.eshop.service.impl;

import cn.gq.eshop.mapper.TbItemDescMapper;
import cn.gq.eshop.mapper.TbItemMapper;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbItemDesc;
import cn.gq.eshop.pojo.TbItemExample;
import cn.gq.eshop.service.IitemService;
import cn.gq.util.DateGridResultPageBean;
import cn.gq.util.EShopResult;
import cn.gq.util.IDUtils;
import cn.gq.util.JsonUtils;
import cn.gq.util.jides.JedisClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements IitemService {
    @Autowired
    TbItemMapper itemdao ;
     @Autowired
     TbItemDescMapper itemDescDao;
    //注入JmsTemplate
    @Autowired
    private JmsTemplate jmsTemplate ;
    @Resource
    //该属性会匹配ID ,name
    private Destination topicDestination ;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO}")
     private String ITEM_INFO ;
    @Value("${ITEM_LIVE}")
     private Integer ITEM_LIVE ;
    @Override
    public DateGridResultPageBean getPageResult(int currentpage, int pagesize) {
        PageHelper.startPage(currentpage,pagesize);
        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = itemdao.selectByExample(example);
        PageInfo<TbItem> info = new PageInfo(tbItems);
        DateGridResultPageBean pageBean = new DateGridResultPageBean(info.getTotal(), tbItems);
        return pageBean;
    }
    @Override
    public TbItem getItemById(long id) {
      //添加缓存来减少数据库的压力
        try {
            //获取缓存中的数据直接返回
            String s = jedisClient.get(ITEM_INFO + ":" + id + ":BASE");
            if(StringUtils.isNotBlank(s))
            { TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItem tbItem = itemdao.selectByPrimaryKey(id);
        //当缓存中没有数据的时候将数据存放redis当中
      try{
          jedisClient.set(ITEM_INFO+":"+id+":BASE",JsonUtils.objectToJson(tbItem));
          //设置数据的存活时间
          jedisClient.expire(ITEM_INFO+":"+id+":BASE",ITEM_LIVE);
      }catch (Exception e) {
       e.printStackTrace();
      }
        //       // 条件查询
//        TbItemExample example = new TbItemExample();
//        TbItemExample.Criteria criteria = example.createCriteria();
//         criteria.andIdEqualTo(id);
//        List<TbItem> tbItems = itemdao.selectByExample(example);
//        if(tbItems.size()>0){
//            return tbItems.get(0);
//        }
        return tbItem;
    }

    @Override
    public EShopResult addItem(TbItem item, String desc) {
        //EShopResult要实现序列化接口
      final   Long id = IDUtils.genItemId();
          //设置上平的ID
          item.setId(id);
          //设置商品的状态//商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        itemdao.insert(item);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDesc.setItemDesc(desc);
        itemDescDao.insert(itemDesc);
        //添加完之后发送消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                //需要等待事务的提交
                TextMessage textMessage = session.createTextMessage(String.valueOf(id));
                return textMessage ;
            }
        });
        return EShopResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long id) {
        //添加缓存来减少数据库的压力
        try {
            //获取缓存中的数据直接返回
            String s = jedisClient.get(ITEM_INFO + ":" + id + ":DESC");
            if(StringUtils.isNotBlank(s)){
            TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);
            return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据商品的ID进行上平描述的查询
        TbItemDesc itemDesc = itemDescDao.selectByPrimaryKey(id);
        try{
            jedisClient.set(ITEM_INFO + ":" + id + ":DESC",JsonUtils.objectToJson(itemDesc));
            //设置数据的存活时间
            jedisClient.expire(ITEM_INFO + ":" + id + ":DESC",ITEM_LIVE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc ;
    }
}
