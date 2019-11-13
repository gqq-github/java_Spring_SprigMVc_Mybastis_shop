package cn.gq.eshop.controller;

import cn.gq.eshop.content.service.IContentService;
import cn.gq.eshop.pojo.TbContent;
import cn.gq.util.DateGridResultPageBean;
import cn.gq.util.EShopResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {
    @Autowired
    IContentService contentService ;
    //以后注意这个拦截路径一定不要写重复
    @RequestMapping("/content/query/list")
    @ResponseBody
    public DateGridResultPageBean  ShowContentList(Long categoryId,Integer page,Integer rows) {
        DateGridResultPageBean contentList = contentService.getContentList(categoryId,page, rows);
        return contentList;
    }
    @RequestMapping ("/content/save")
    @ResponseBody
    public EShopResult AddContent(TbContent content){
        EShopResult result = contentService.insertContent(content);
        return result ;
    }
}
