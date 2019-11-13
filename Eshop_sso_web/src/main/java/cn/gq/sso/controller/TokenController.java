package cn.gq.sso.controller;

import cn.gq.eshop.sso.service.ITokenService;
import cn.gq.util.EShopResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {
    @Autowired
    ITokenService tokenService ;
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    //拦截token的请求
    //解决的跨域的俩中方式
    //1.就是讲请求的数据拼接为一个js 的代码 想给给前端请求
    //2.利用spring来自动拼接 MappingJacksonValue
    public Object getUserByToken(@PathVariable String token,String callback){
        EShopResult result = tokenService.UserToken(token);
        if(StringUtils.isNotBlank(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue ;
        }
        return result ;
    }
}
