package shop;

import cn.gq.util.jides.JedisClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_jedis_Sping {
    public static void main(String[] args) {
        ApplicationContext aco =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient bean = aco.getBean(JedisClient.class);
        bean.set("sp","ps") ;
        System.out.println(bean.get("sp"));
    }
}
