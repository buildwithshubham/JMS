package com.shubham;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FirstQueue {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection=null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = cf.createConnection();
            Session session = connection.createSession();
            Queue queue= (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("I am createot my destini");
            producer.send(message);
            System.out.println("MessageSend "+message.getText());

            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            TextMessage messageRecived = (TextMessage) consumer.receive(5000);
            System.out.println("MessageRecived "+messageRecived.getText());

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(initialContext!=null){
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}