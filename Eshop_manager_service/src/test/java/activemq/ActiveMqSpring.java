package activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class ActiveMqSpring {
     //
    @Test
    public void testSpringMq () {
        //初始化Spring容器
        ApplicationContext app =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
        //获取模板对象
        JmsTemplate bean = app.getBean(JmsTemplate.class);
        // 第三步：从容器中获得一个Destination对象
        Destination queueDestination = (Destination) app.getBean("queueDestination");
        bean.send(queueDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage mq = session.createTextMessage("hello Spring mq");
                return mq;
            }
        });
    }
}
