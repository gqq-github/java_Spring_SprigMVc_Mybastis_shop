package cn.gq.order.controller.interceptor;

import cn.gq.eshop.car.service.ICartService;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.sso.service.ILoginService;
import cn.gq.eshop.sso.service.ITokenService;
import cn.gq.util.CookieUtils;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CheckLoginInterceptor implements HandlerInterceptor {
    @Value("${SSO_URL}")
    private String SSO_URL ;
    @Autowired
    ITokenService tokenService ;
    @Autowired
    ICartService cartService ;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //判断TT_TOKEN 是否存在数据 不存在怎用户登录 记录当前的请求的URL
        String tt_token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        StringBuffer currentUrl = httpServletRequest.getRequestURL();
        if(StringUtils.isBlank(tt_token)){
            httpServletResponse.sendRedirect(SSO_URL+"/page/login?redirect="+currentUrl);
            return  false ;
        }
        //存在则在sso单点系统中判断用户登录是否已经过期
        EShopResult result = tokenService.UserToken(tt_token);
        //过期则跳转到登录页面 并记录用户当前所在URL
        if(result.getStatus()!=200){
            httpServletResponse.sendRedirect(SSO_URL+"/page/login?redirect="+currentUrl);
            return  false ;
        }
        //如果用户没有过期
        TbUser user = (TbUser) result.getData();
        //则把用户的信息保存request当中
         httpServletRequest.setAttribute("user",user);
        //判断cookie当中的购物信息 有则加到redis当中
        String json = CookieUtils.getCookieValue(httpServletRequest, "TT_CART",true);
        if(StringUtils.isNotBlank(json)){
            List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
              cartService.mergeCart(user.getId(),list);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
