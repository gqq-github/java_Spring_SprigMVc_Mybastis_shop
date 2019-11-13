package cn.gq.item.details.controller.listener;

import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbItemDesc;
import cn.gq.eshop.service.IitemService;
import cn.gq.item.details.controller.pojo.Item;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HtmlGenListener implements MessageListener {
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH ;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer ;
    @Autowired
    private IitemService itemService;
    @Override
    public void onMessage(Message message) {
        //获取消息
        TextMessage textMessage = (TextMessage) message;

        //获取商品对象和商品描述对面
        try {
            String text = textMessage.getText();
            long id =  Long.valueOf(text);
            //等待事务的提交
            Thread.sleep(1000);
            TbItem itemById = itemService.getItemById(id);
            Item item = new Item(itemById);
            TbItemDesc itemDescById = itemService.getItemDescById(id);
            //获得从Configuration
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //获取模板对象参照item.ftl
            Template template = configuration.getTemplate("item.ftl");
            //创建数据集
            Map map = new HashMap();
            map.put("item",item);
            map.put("itemDesc",itemDescById);
            //创建数据流将静态文件
            Writer out = new FileWriter(HTML_GEN_PATH+"\\"+id+".html");
            //process
            template.process(map,out);
            //关闭流
            out.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
}
