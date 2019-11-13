package cn.gq.sso.controller;

import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.sso.service.IRegisterService;
import cn.gq.util.EShopResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
    @Autowired
    IRegisterService registerService ;
    @RequestMapping("/page/register")
    public String ShowRegisterPage() {
        return "register" ;
    }
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EShopResult checkData(@PathVariable String param ,@PathVariable Integer type){
        EShopResult dateType = registerService.findDateType(param, type);
        return dateType;
    }
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public EShopResult  Register (TbUser user){
        EShopResult result = registerService.createUser(user);
        return  result ;
    }
}
