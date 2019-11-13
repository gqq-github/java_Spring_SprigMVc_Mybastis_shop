package cn.gq.eshop.search.listener;

import cn.gq.eshop.search.mapper.ISearchItem;
import cn.gq.util.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

public class SynSolrAddMessageListener implements MessageListener {
    @Autowired
    private SolrServer solrServer ;
    @Autowired
    ISearchItem searchItem ;
    @Override
    public void onMessage(Message message) {
         //添加记录 的时候将添加的商品的ID作为消息来传递
        TextMessage textMessage = (TextMessage) message;

        try {
            long id = Long.valueOf(textMessage.getText());
             //在数据库中查找刚才添加的数据添加到索引库
            Thread.sleep(1000);
            SearchItem si = searchItem.getSearchItemById(id);
            //添加到索引库
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", si.getId());
            document.addField("item_title", si.getTitle());
            document.addField("item_sell_point", si.getSell_point());
            document.addField("item_price", si.getPrice());
            document.addField("item_image", si.getImage());
            document.addField("item_category_name", si.getCategory_name());
            solrServer.add(document);
            //将数据同步到索引库
            solrServer.commit();
            //需要在配置文件中配置这个类
        } catch (JMSException e) {

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
