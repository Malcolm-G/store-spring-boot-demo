package com.malcolm.notification;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.malcolm.notification.payload.OrderCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderEventConsumer {

	/*
	 * @RabbitListener(queues = "${rabbitmq.queue.name}") public void
	 * handleOrderEvent(OrderCreatedEvent orderEvent) {
	 * System.out.println("Received Order Event: " + orderEvent);
	 * 
	 * Long orderId = orderEvent.getOrderId(); OrderStatus orderStatus =
	 * orderEvent.getOrderStatus();
	 * 
	 * System.out.println("Order ID: " + orderId);
	 * System.out.println("Order Status: " + orderStatus); }
	 */
	
	@Bean
	Consumer<OrderCreatedEvent> orderCreated(){
		return event -> {
			log.info("Received order created event for order ID: {}", event.getOrderId());
			log.info("Received order created event for user ID: {}", event.getUserId());
		};
	}
}
