package com.shubham;

import org.w3c.dom.Text;

import javax.jms.*;
import javax.naming.InitialContext;

public class FirstTopic {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection =null;

        try{
            initialContext = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("ConnectionFactory");
            connection = cf.createConnection();

            Session session = connection.createSession();
            Topic topic = (Topic) initialContext.lookup("topic/myTopic");
            MessageProducer producer = session.createProducer(topic);

            MessageConsumer consumer1 = session.createConsumer(topic);
            MessageConsumer consumer2 = session.createConsumer(topic);

            TextMessage message = session.createTextMessage("Hello 1");

            connection.start();
            producer.send(message);



            TextMessage tm1 =(TextMessage) consumer1.receive();
            TextMessage tm2= (TextMessage) consumer2.receive();

            System.out.println("Consumer 1 messgae recived "+tm1.getText());
            System.out.println("Consumer 2 messgae recived "+tm2.getText());





        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
