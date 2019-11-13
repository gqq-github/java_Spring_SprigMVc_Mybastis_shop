package cn.gq.eshop.search.service.impl;

import cn.gq.eshop.search.dao.SearchDao;
import cn.gq.eshop.search.service.ISearchService;
import cn.gq.util.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 实现的商品的查询服务
 */
@Service
public class SearchServiceImpl implements ISearchService {
   @Autowired
    SearchDao searchDao ;
    public SearchResult search(String keyword, Integer page, Integer rows) throws SolrServerException {
      // 设置一个query对象
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setStart((page-1)*rows);
        query.setRows(rows);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        SearchResult results = searchDao.Search(query);
        int totalpage = (results.getRecourdCount()+rows)/rows;
        results.setTotalPages(totalpage);
        return  results;
    }
}
