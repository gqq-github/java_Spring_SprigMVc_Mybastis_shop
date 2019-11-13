package slorTest.cloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

public class TestCloud {
    public static void cloudinsert() throws IOException, SolrServerException {
        // 首先将所需的jar包导入
        //建立一个CloudServer对象
        //需要的参数是zookepper的地址参数
        SolrServer solrServer =  new CloudSolrServer("192.168.25.129:2182,192.168.25.129:2183,192.168.25.129:2184");
        //设置一个默认连接的collection
        ((CloudSolrServer) solrServer).setDefaultCollection("collection2");
        // 之后的和单机版是一样的
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品");
        document.addField("item_price","10");
        solrServer.add(document);
        solrServer.commit();
     }

     public static void cloudQuery() throws SolrServerException {

         // 首先将所需的jar包导入
         //建立一个CloudServer对象
         //需要的参数是zookepper的地址参数
         SolrServer solrServer =  new CloudSolrServer("192.168.25.129:2182,192.168.25.129:2183,192.168.25.129:2184");
         //设置一个默认连接的collection
         ((CloudSolrServer) solrServer).setDefaultCollection("collection2");
         SolrQuery query = new SolrQuery();
         query.setQuery("id:test001");
         QueryResponse query1 = solrServer.query(query);
         SolrDocumentList results = query1.getResults();
         System.out.println(results.getNumFound());
         for (SolrDocument document:results){
             System.out.println(document.get("id"));
             System.out.println(document.get("item_title"));
             System.out.println(document.get("item_price"));
         }
     }

    public static void main(String[] args) throws IOException, SolrServerException {
        //TestCloud.cloudinsert();
        TestCloud.cloudQuery();
    }
}
