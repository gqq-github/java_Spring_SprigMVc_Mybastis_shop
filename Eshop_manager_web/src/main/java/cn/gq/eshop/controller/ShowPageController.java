package cn.gq.eshop.controller;

import cn.gq.util.DateGridResultPageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {
     @RequestMapping("/")
    public String showIndex () {
      return "index" ;
  }
  @RequestMapping("/{page}")
  public String showPage(@PathVariable String page){
      return page ;
  }

}
