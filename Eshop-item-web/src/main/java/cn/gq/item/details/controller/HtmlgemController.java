package cn.gq.item.details.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HtmlgemController {
 @Autowired
 FreeMarkerConfigurer freeMarkerConfigurer ;
 @RequestMapping("/htmlgem")
    @ResponseBody
    public String genHtm() throws IOException, TemplateException {
     // 1、从spring容器中获得FreeMarkerConfigurer对象。
     // 2、从FreeMarkerConfigurer对象中获得Configuration对象。
     Configuration configuration = freeMarkerConfigurer.getConfiguration();
     // 3、使用Configuration对象获得Template对象。
     Template template = configuration.getTemplate("hello.ftl");
       Map map = new HashMap<>();
       map.put("hello","hello spring and freemaker") ;
     Writer out = new FileWriter(new File("E:\\BaiduNetdiskDownload\\6第六阶段 项目实战二（14天）\\宜立方商城(14天）\\e3商城_day01\\黑马32期\\01.教案-3.0\\02.教案-AB-3.0\\freemaker\\hello.html"));
     template.process(map,out);
     out.close();
     return "ok";
 }
}
