package cn.gq.eshop.car.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class StartupCarService {
    public static void main(String[] args) throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
       System.in.read();
    }
}
