package activemq;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

public class ActiveMqTest {
    @Test
    public  void testProduct() throws JMSException {
        //1.创建一个connectionFactory对象需要指定服务端IP及端口号
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
        //brokeURl服务器的IP极端口
        //使用connectionFactory创建一个connection对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建一个session对象
        //第一个参数表示是否开启事务,如果开启第二个蚕食没有意义
        //第二个参数表示的是消息是自动接收还是手动接收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //使用session对象创建Destination对象(topic/queue)
        //创建一个Queue对对象
        Queue queue = session.createQueue("test-queue");
        //使用session创建一个producer 对象
        MessageProducer producer = session.createProducer(queue);
        //创建一个message 创建一个TestMessage对象
//          TextMessage message = new ActiveMQTextMessage();
//          message.setText("hello mq");
        TextMessage hello_mq = session.createTextMessage("hello mq");
        //使用producer 发送消息
          producer.send(hello_mq);
        //关闭资源
        producer.close();
        session.close();
        connection.close();

    }
@Test
    public void testConsumer() throws JMSException, IOException {
    //1.创建一个connectionFactory对象需要指定服务端IP及端口号
    ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
    //brokeURl服务器的IP极端口
    //使用connectionFactory创建一个connection对象
    Connection connection = connectionFactory.createConnection();
    //开启连接
    connection.start();
    //创建一个session对象
    //第一个参数表示是否开启事务,如果开启第二个蚕食没有意义
    //第二个参数表示的是消息是自动接收还是手动接收
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Queue queue = session.createQueue("spring-queue");
    MessageConsumer consumer = session.createConsumer(queue);
    consumer.setMessageListener(new MessageListener() {
        @Override
        public void onMessage(Message message) {
            TextMessage textMessage = (TextMessage) message;
            String text =  "" ;
            try {
               text=textMessage.getText();
                System.out.println(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    });
    //保证监听的存在
    System.in.read();
    consumer.close();
    session.close();
    connection.close();

}
    @Test
    public void testTopicProducer() throws Exception {
        // 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
        // brokerURL服务器的ip及端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
        // 第二步：使用ConnectionFactory对象创建一个Connection对象。
        Connection connection = connectionFactory.createConnection();
        // 第三步：开启连接，调用Connection对象的start方法。
        connection.start();
        // 第四步：使用Connection对象创建一个Session对象。
        // 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        // 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个topic对象。
        // 参数：话题的名称。
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Producer对象。
        MessageProducer producer = session.createProducer(topic);
        // 第七步：创建一个Message对象，创建一个TextMessage对象。
        /*
         * TextMessage message = new ActiveMQTextMessage(); message.setText(
         * "hello activeMq,this is my first test.");
         */
        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my topic test");
        // 第八步：使用Producer对象发送消息。
        producer.send(textMessage);
        // 第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public void testTopicConsumer() throws Exception {
        // 第一步：创建一个ConnectionFactory对象。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
        // 第二步：从ConnectionFactory对象中获得一个Connection对象。
        Connection connection = connectionFactory.createConnection();
        // 第三步：开启连接。调用Connection对象的start方法。
        connection.start();
        // 第四步：使用Connection对象创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Consumer对象。
        MessageConsumer consumer = session.createConsumer(topic);
        // 第七步：接收消息。
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = null;
                    // 取消息的内容
                    text = textMessage.getText();
                    // 第八步：打印消息。
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("topic的消费端03。。。。。");
        // 等待键盘输入
        System.in.read();
        // 第九步：关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

}
