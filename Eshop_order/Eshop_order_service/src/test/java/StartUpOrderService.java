import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartUpOrderService {
    public static void main(String[] args) throws IOException {
         //service只是相当加载了Spring的配置文件
         //反面的来说Douboo的客户端任意
        ApplicationContext app =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        System.in.read();
    }
}
