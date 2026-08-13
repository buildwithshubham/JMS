package com.shubham;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class QueueBrowse {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection=null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = cf.createConnection();
            Session session = connection.createSession();
            Queue queue= (Queue)initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);

            TextMessage message = session.createTextMessage("msg1");
            TextMessage message1 = session.createTextMessage("msg2");


            QueueBrowser browser = session.createBrowser(queue);
            Enumeration messageEnum = browser.getEnumeration();

            System.out.println("MessageSend "+message.getText());
            System.out.println("MessageSend "+message1.getText());
            connection.start();
            producer.send(message);
            producer.send(message1);


            while(messageEnum.hasMoreElements()){
                TextMessage eachMessage = (TextMessage) messageEnum.nextElement();
                System.out.println("Browsing queue" +eachMessage.getText());
            }

            MessageConsumer consumer = session.createConsumer(queue);
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