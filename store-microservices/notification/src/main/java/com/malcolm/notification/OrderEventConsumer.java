package com.malcolm.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.malcolm.notification.payload.OrderCreatedEvent;
import com.malcolm.notification.payload.OrderStatus;

@Service
public class OrderEventConsumer {

	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public void handleOrderEvent(OrderCreatedEvent orderEvent) {
		System.out.println("Received Order Event: " + orderEvent);

		Long orderId = orderEvent.getOrderId();
		OrderStatus orderStatus = orderEvent.getOrderStatus();

		System.out.println("Order ID: " + orderId);
		System.out.println("Order Status: " + orderStatus);
	}
}
