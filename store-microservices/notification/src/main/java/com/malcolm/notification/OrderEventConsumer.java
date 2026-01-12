package com.malcolm.notification;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public void handleOrderEvent(Map<String, Object> orderEvent) {
		System.out.println("Received Order Event: " + orderEvent);

		Long orderId = Long.valueOf(orderEvent.get("orderId").toString());
		String orderStatus = orderEvent.get("status").toString();

		System.out.println("Order ID: " + orderId);
		System.out.println("Order Status: " + orderStatus);
	}
}
