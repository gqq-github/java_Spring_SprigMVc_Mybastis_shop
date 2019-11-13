package cn.gq.eshop.content.service.service.impl.test.StartUp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartupContent {
    public static void main(String[] args) throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
       System.in.read();
    }
}
