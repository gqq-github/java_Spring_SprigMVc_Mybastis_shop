package cn.gq.eshop.car.controller.intercepter;

import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.sso.service.ITokenService;
import cn.gq.util.CookieUtils;
import cn.gq.util.EShopResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.transform.impl.InterceptFieldFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor implements HandlerInterceptor {
    @Autowired
    ITokenService tokenService ;
     //handler执行之前
    /***
     * 主要实现的功能
     * 如果用户登录则将其信息保存到request域当中
     * 但是不论其存在还是不存在都放行
     * */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //在cookie 当中获取token
        //需要sso的支持
        String tt_token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
         if(StringUtils.isBlank(tt_token)){
             return true ;
         }
         //sso获取用户的token信息
        EShopResult result = tokenService.UserToken(tt_token);
         //表示用户已经过期 ==400
         if(result.getStatus()==400){
              return true ;
         }
         //表示用户已经登录将其信息添加到request的域当中用于验证
         if(result.getStatus()==200){
             TbUser user = (TbUser) result.getData();
              httpServletRequest.setAttribute("user",user);
              return  true ;
         }

         return true;
         //该方法看起来没有起到拦截的作用但是实现的AOP的思想
        //减少了曾删改的用户是否登录的验证
    }
    //handler执行之后
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
   //handler编译之后
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
