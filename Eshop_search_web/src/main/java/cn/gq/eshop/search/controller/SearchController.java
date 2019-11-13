package cn.gq.eshop.search.controller;

import cn.gq.eshop.search.service.ISearchService;
import cn.gq.util.pojo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品查询的控制层
 */

@Controller
public class SearchController{
   @Value("${SEARCH_DF_ROWS}")
    //商品显示的默认条数
    Integer SEARCH_DF_ROWS;
   @Autowired
    ISearchService searchService ;

   @RequestMapping("/search")
    public String  search (String keyword, @RequestParam(defaultValue = "1")
           Integer page , Model model) throws Exception {
       //需要进行转码
       keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
       SearchResult result = searchService.search(keyword, page, SEARCH_DF_ROWS);
       model.addAttribute("query", keyword);
       model.addAttribute("totalPages", result.getTotalPages());
       model.addAttribute("recourdCount", result.getRecourdCount());
       model.addAttribute("page", page);
       model.addAttribute("itemList", result.getItemList());
       return "search" ;
   }
}
