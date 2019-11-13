package cn.gq.eshop.search.service.impl;

import cn.gq.eshop.search.mapper.ISearchItem;
import cn.gq.eshop.search.service.ISearchItemService;
import cn.gq.util.EShopResult;
import cn.gq.util.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * solr实现商品查询的服务
 * **/
public class SearchItemServiceImpl implements ISearchItemService {
     // 需要条件他的类路劲到application-dao 文件中
     // 让sprin个帮助我们创建解接口的实例
    @Autowired
    ISearchItem iSearchItem ;
    @Autowired
    SolrServer solrServer ;
    @Override
    //将数据库中的信息导入到索引库当中
    public EShopResult findAllSearchItem() {
         //将这个方法交由Spring去创建
        try{
        List<SearchItem> searchItems = iSearchItem.searchItemAll();

        for (SearchItem si : searchItems){
            // 这个document 对象要建立在循环里面
            // 否则下一次的创建就是一个对象
            //ID的值就会重复
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", si.getId());
            document.addField("item_title", si.getTitle());
            document.addField("item_sell_point", si.getSell_point());
            document.addField("item_price", si.getPrice());
            document.addField("item_image", si.getImage());
            document.addField("item_category_name", si.getCategory_name());
            solrServer.add(document);

        }
            solrServer.commit();
            return EShopResult.ok();
        }catch (Exception e) {
            e.printStackTrace();
            return EShopResult.build(500,"数据导入失败");
        }

    }
}
