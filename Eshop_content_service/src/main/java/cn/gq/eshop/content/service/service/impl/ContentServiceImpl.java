package cn.gq.eshop.content.service.service.impl;

import cn.gq.eshop.content.service.IContentService;
import cn.gq.eshop.mapper.TbContentMapper;
import cn.gq.eshop.pojo.TbContent;
import cn.gq.eshop.pojo.TbContentExample;
import cn.gq.util.DateGridResultPageBean;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import cn.gq.util.jides.JedisClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ContentServiceImpl implements IContentService {
    @Autowired
    TbContentMapper contentDao ;
    @Autowired
    JedisClient jedisClient ;
    @Value("${CONTENT_LIST}")
    String CONTENT_LIST ;
    public DateGridResultPageBean getContentList(long categoryId,int page, int rows) {
        // 进行分页查询
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentDao.selectByExample(example);
        PageInfo<TbContent> info = new PageInfo<>(list);
        Long tatal = info.getTotal();
        DateGridResultPageBean pageBean = new DateGridResultPageBean(tatal,list);
        return pageBean;
    }
    // 实现TbContent的添加功能
    // 里面用到的图片上传是有PictureUpController
    // 这方法返回的是文件在StorageService 中的地址
    // map.put("error",0);
    //    map.put("url",url);

    public EShopResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date ());
        contentDao.insert(content);
      //图片信息插入的时候讲缓存清除 第二次加载的时候就是新数据
        try{
            jedisClient.hdel(CONTENT_LIST,content.getCategoryId()+"");
            System.out.println("将缓存中的数据清除");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return EShopResult.ok();
    }
     //根据分类的ID来查询这个广告分类需要展示的内容
     //利用缓存来减少数据库的压力
    public List<TbContent> getContentListByCategoryId(Long categoryid) {
        //首先得判断redis中是否存在缓存
      try{

          String hget = jedisClient.hget(CONTENT_LIST, categoryid + "");
           if(StringUtils.isNotBlank(hget)){
               System.out.println("在缓存中读取数据");
                //将字符串转换为List
               List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
               return  list ;
           }
      }catch (Exception e){
          e.printStackTrace();
      }
        //存在则直接加载redis中的数据
        //防止因redis出现问题导致我们之后的业务逻辑没有办法进行
        //try catch 捕获异常
        TbContentExample example = new TbContentExample();
        //根据CategoryID来匹配
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryid);
         //这个WithBLBs的作用是,将文本数据也查询出来
        List<TbContent> tbContents = contentDao.selectByExampleWithBLOBs(example);
        // 将结果查询到之后应该存入redis数据库当中
         try{
             String s = JsonUtils.objectToJson(tbContents);
             System.out.println("将数据加入到缓存当中");
             jedisClient.hset(CONTENT_LIST,categoryid+"",s);
         }catch (Exception e ){
             e.printStackTrace();
         }
        return tbContents;
        //这里其实有一个问题当数据更新的时候应该清楚缓存
        //在进行添加数据的时候讲过缓存清空
    }
}
