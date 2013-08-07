/**
 * 
 */
package org.vidad.tools.nosql;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author moshe
 *
 */
public class RabbitMQ {
	public final static String DEFAULT_EXCHANGE = "taptica";
	public final static String DEFAULT_QUEUE 	= "RTB";
	Boolean durable = true;
	
	ConnectionFactory	factory;
	Connection 			connection;
	Channel				channel;
	
	public interface MQConsumer{
		void consume(String message);
	}
	
    public RabbitMQ(String host) throws IOException {
    	
    }
    public RabbitMQ(String host, String username, String password) throws IOException {
		super();
		factory = new ConnectionFactory();

		factory.setHost(host);
	    factory.setUsername(username);
	    factory.setPassword(password);

	    connection = factory.newConnection();
	    channel = connection.createChannel();
	}
    
    //see http://www.rabbitmq.com/tutorials/amqp-concepts.html
    public void createExchange(String exchange) throws IOException {
        channel.exchangeDeclare( exchange, "amq.direct", durable);
    }
    
    public void queueExchangeDeclare(String queue, String exchange, String rountingKey) throws IOException {
    	channel.queueDeclare( queue==null? DEFAULT_QUEUE:queue, durable, false, false, null);
        channel.queueBind( queue==null? DEFAULT_QUEUE:queue, exchange==null? DEFAULT_EXCHANGE:exchange, rountingKey);
    }
    
    public void queueDeclare() throws IOException {
    	channel.queueDeclare( DEFAULT_QUEUE, durable, false, false, null);
    }
    
    public void publish(String message) throws IOException {
    	publish( message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }
    
    public void publish(byte[] bytes) throws IOException {
        channel.basicPublish("", DEFAULT_QUEUE, null, bytes);
	}

	public void consume(MQConsumer mqconsumer) throws IOException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(DEFAULT_QUEUE, true, consumer);

        while (true) {
          QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			String message = new String(delivery.getBody());
			mqconsumer.consume(message);
			//System.out.println(" [x] Received '" + message + "'");
        }
    }
//	Topic
//	Check hive to mongo
//	kiji.org - hbase to real time.
}	
