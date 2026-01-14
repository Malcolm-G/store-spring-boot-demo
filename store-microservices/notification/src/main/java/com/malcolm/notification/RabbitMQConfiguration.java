/*
 * package com.malcolm.notification;
 * 
 * import org.springframework.amqp.core.AmqpAdmin; import
 * org.springframework.amqp.core.Binding; import
 * org.springframework.amqp.core.BindingBuilder; import
 * org.springframework.amqp.core.ExchangeBuilder; import
 * org.springframework.amqp.core.Queue; import
 * org.springframework.amqp.core.QueueBuilder; import
 * org.springframework.amqp.core.TopicExchange; import
 * org.springframework.amqp.rabbit.connection.ConnectionFactory; import
 * org.springframework.amqp.rabbit.core.RabbitAdmin; import
 * org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
 * import org.springframework.amqp.support.converter.MessageConverter; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration;
 * 
 *//**
	 * After transitioning to spring cloud streams you don't really need the config
	 * but it is still used when you still need more fine-tuned control
	 *//*
		 * @Configuration public class RabbitMQConfiguration {
		 * 
		 * @Value("${rabbitmq.queue.name}") private String queueName;
		 * 
		 * @Value("${rabbitmq.exchange.name}") private String exchangeName;
		 * 
		 * @Value("${rabbitmq.routing.key}") private String routingKey;
		 * 
		 * @Bean Queue queue() { return QueueBuilder.durable(queueName).build(); }
		 * 
		 * @Bean TopicExchange topicExchange() { return
		 * ExchangeBuilder.topicExchange(exchangeName).durable(true).build(); }
		 * 
		 * @Bean Binding binding() { return
		 * BindingBuilder.bind(queue()).to(topicExchange()).with(routingKey); }
		 * 
		 * @Bean AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) { RabbitAdmin
		 * admin = new RabbitAdmin(connectionFactory); admin.setAutoStartup(true);
		 * return admin; }
		 * 
		 * @Bean MessageConverter messageConverter() { return new
		 * Jackson2JsonMessageConverter(); } }
		 */