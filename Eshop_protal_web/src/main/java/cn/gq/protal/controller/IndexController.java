package cn.gq.protal.controller;

import cn.gq.eshop.content.service.IContentService;
import cn.gq.eshop.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    IContentService contentService;
    //这个Value取值的时候一定要是${}才能取出来具体的值
    @Value("${CONTENT_BUILD_ID}")
    Long categoryId;
    @RequestMapping("/index")
    //Model封装的数据在页面进行解析
    public String ShowIndex(Model model){
        List<TbContent> list = contentService.getContentListByCategoryId(categoryId);
        model.addAttribute("ad1List",list) ;
        return  "index" ;
 }
}
