package cn.gq.eshop.search.dao;

import cn.gq.util.pojo.SearchItem;
import cn.gq.util.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 实现商品查询的后台
 */
@Repository
public class SearchDao {
    //注入slorServer
    @Autowired(required=true)
    SolrServer solrServer ;
    public SearchResult  Search (SolrQuery query) throws SolrServerException {
        QueryResponse query1 = solrServer.query(query);
         //获取查询的结果
        SolrDocumentList results = query1.getResults();
        //获取高亮显示的对象
        Map<String, Map<String, List<String>>> highlighting = query1.getHighlighting();
        List<SearchItem> list = new ArrayList<>() ;
        for (SolrDocument solrDocument : results) {
            SearchItem searchItem = new SearchItem();
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            List<String> hlis = highlighting.get(solrDocument.get("id")).get("item_title");
               if( hlis!=null&&hlis.size()>0){
                      searchItem.setTitle(hlis.get(0));
               }
               else {
                   searchItem.setTitle((String) solrDocument.get("item_title"));
               }
               list.add(searchItem);
        }
         SearchResult searchResult = new SearchResult() ;
        searchResult.setItemList(list);
         //设置总记录数
        searchResult.setRecourdCount((int) results.getNumFound());
        return searchResult ;
    }
}
