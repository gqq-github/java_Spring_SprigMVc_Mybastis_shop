package cn.gq.eshop.search.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionReslover implements HandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);
    @Override
    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) {
        //写日志文件
        //日志文件需要配置
        logger.error("系统发生异常", e);
        //发邮件、发短信
        //Jmail：可以查找相关的资料
        //需要在购买短信。调用第三方接口即可。
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常，请稍后重试");
        modelAndView.setViewName("error/exception");
        //需要在springmvc 中配置全局的异常的捕获
        return modelAndView;
    }

}