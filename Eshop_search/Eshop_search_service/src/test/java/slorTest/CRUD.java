package slorTest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
  * 测试slorJ的CRUD
  * **/
public class CRUD {

    public void create () throws IOException, SolrServerException {
    // 将slorj 的jar包导入到工程当中
    //创建一个SlorjServer
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.129:8080/solr");
        // 创建一个Document 的对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test1");
        document.addField("item_title","test");
        //将文档对对象加入到Solr加到索引库当中
        solrServer.add(document);
        // 提交这个是事务
        solrServer.commit();
    }
    public void query() throws SolrServerException {
        //获取solr的服务
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.129:8080/solr");
         //获取查询的条件
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        // 简单查询
        //query.set("q","*:*");
         query.setQuery("*:*") ;
         // 以上的俩者等价
        // 获取查询response
        QueryResponse query1 = solrServer.query(query);
        //获取查询结果
         // SolrDocumentList 的父类其实是ArrayList
        SolrDocumentList results = query1.getResults();
         //根据查询的document的对象来封装得到的结果
          //result的结果对象能够获取总记录数
         //  SolrDocumentList 相当于我们在后台的界面看到的一个大括号
         // 他封装的就是那个内容
        System.out.println("总记录数"+results.getNumFound());
         for (SolrDocument document:results){
            System.out.println(document.get("id"));
             System.out.println(document.get("item_title"));
        }
    }
    public void queryFuza () throws SolrServerException {
      // 进行复杂查询和高亮显示
        //获取solr的服务
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.129:8080/solr");
        //获取查询的条件
        SolrQuery query = new SolrQuery();
         // 高亮显示利用他的set 方法来时实现
         // 设置查询的关键字
        query.setQuery("手机");
        // 当然需要设置默认的查询关键字
        //如果不设置默认的关键字slor内部的默认关键字是text
        //那么查出的结果就是空
         query.set("df","item_title");
         //设置起始页
         query.setStart(0);
         //设置查询的条数
         query.setRows(4);
         //开启高亮
        query.setHighlight(true);
         // 添加高亮显示的域
        query.addHighlightField("item_title");
        //设置高亮显示的标签
        QueryResponse query1 = solrServer.query(query);
         //QueryResPonse中保存着高亮显示的map
         // 形式 (key1,(key2,List));
         // key1 中保存的是ID
         // key2=当前高亮域的名称的名称
         // 保存高亮的数据(简单来说就是html标签)
        Map<String, Map<String, List<String>>> highlighting = query1.getHighlighting();
        // 获取document对象 // ArrayList
        SolrDocumentList results = query1.getResults();
        for (SolrDocument entries:results){
            //显示高亮显示
            Map<String, List<String>> id = highlighting.get(entries.get("id"));
            List<String> item_title = id.get("item_title");
            // 这个判断是为了保证list的对象不为null
            //null的产生的原因是设置了有三个keywords
            if(item_title.size()>0 && item_title!=null){
                System.out.println(item_title.get(0));
            }
        }

    }
    public static void main(String[] args) throws IOException, SolrServerException {
         CRUD crud = new CRUD() ;
      //   crud.create();
        // crud.query();
         crud.queryFuza();
    }
}
