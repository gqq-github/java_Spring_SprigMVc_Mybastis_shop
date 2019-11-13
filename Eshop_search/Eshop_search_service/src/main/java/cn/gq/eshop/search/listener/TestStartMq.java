package cn.gq.eshop.search.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestStartMq {
    public static void main(String[] args) throws IOException {
        ApplicationContext app  = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
        System.in.read();
    }

}
