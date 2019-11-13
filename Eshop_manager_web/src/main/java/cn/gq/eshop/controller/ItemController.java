package cn.gq.eshop.controller;

import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.service.IitemService;
import cn.gq.util.DateGridResultPageBean;
import cn.gq.util.EShopResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.print.attribute.standard.Destination;

//todo 没有完成商品的修改的和删除操作
@Controller
public class ItemController {
    @Autowired
    public IitemService service;

 @RequestMapping("/item/{itemid}")
 @ResponseBody
 public TbItem getItemById(@PathVariable long itemid){
     return service.getItemById(itemid);
  }
    @RequestMapping("/item/list")
    @ResponseBody
    public DateGridResultPageBean getItemList(Integer page,Integer rows) {
      //page表示的是当前页
        //rows表示页面的大小
        DateGridResultPageBean pageResult = service.getPageResult(page, rows);
        return pageResult;
    }
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public EShopResult addItem (TbItem item ,String desc ) {
        EShopResult eShopResult = service.addItem(item, desc);
        // 因为考虑到消息发送但是数据却没有更新到数据库当中
        //所以在这里进行消息的发送
        return  eShopResult ;
    }
}
