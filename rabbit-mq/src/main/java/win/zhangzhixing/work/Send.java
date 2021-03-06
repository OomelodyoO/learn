package win.zhangzhixing.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/zhang");
        factory.setUsername("zhang");
        factory.setPassword("zhang");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(
                QUEUE_NAME, false, false,
                false, null
        );
        for (int i = 0; i < 50; i++) {
            String message = "message:\t" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(message);
            try {
                Thread.sleep(i * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();
    }
}