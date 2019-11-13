package cn.gq.eshop.controller;

import cn.gq.eshop.search.service.ISearchItemService;
import cn.gq.util.EShopResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//导入索引库的控制层
public class SearchController {
     @Autowired
    ISearchItemService searchItemService ;
    @RequestMapping("/index/item/import")
    @ResponseBody
    public EShopResult exportDate() {
        EShopResult allSearchItem = searchItemService.findAllSearchItem();
        return  allSearchItem ;
    }
}
