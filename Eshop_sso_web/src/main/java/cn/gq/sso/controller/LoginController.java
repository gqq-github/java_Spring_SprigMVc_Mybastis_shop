package cn.gq.sso.controller;

import cn.gq.eshop.sso.service.ILoginService;
import cn.gq.util.CookieUtils;
import cn.gq.util.EShopResult;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
 @Autowired
    ILoginService loginService;
 @Value("${COOKIE_KEY}")
  private String COOKIE_KEY;
 @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
     model.addAttribute("redirect",redirect);
     return "login";
 }
 @RequestMapping(value = "/user/login",method = RequestMethod.POST)
 @ResponseBody
 public EShopResult Login(String username , String password, HttpServletRequest request ,
                          HttpServletResponse response){
     EShopResult result = loginService.UserLogin(username, password);
     Integer status = result.getStatus();
     if(status==200){
         //将token写入cooke
         String data =  result.getData().toString();
         CookieUtils.setCookie(request,response,COOKIE_KEY,data);
     }
     return result ;
 }
}
