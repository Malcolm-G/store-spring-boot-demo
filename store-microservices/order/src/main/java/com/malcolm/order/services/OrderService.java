package com.malcolm.order.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.malcolm.order.dto.OrderCreatedEvent;
import com.malcolm.order.mappers.OrderItemMapper;
import com.malcolm.order.models.CartItem;
import com.malcolm.order.models.Order;
import com.malcolm.order.models.OrderItem;
import com.malcolm.order.models.OrderStatus;
import com.malcolm.order.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final RabbitTemplate rabbitTemplate;
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	private final OrderItemMapper orderItemMapper;

//	@Autowired
//	private final UserRepository userRepository;

	public Optional<Order> createOrder(String userId) {
		/*
		 * // Validate the user Optional<User> userOpt =
		 * userRepository.findById(Long.valueOf(userId));
		 * 
		 * if (userOpt.isEmpty()) { return Optional.empty(); } User user =
		 * userOpt.get();
		 */

		// Validate cart items
		List<CartItem> cartItems = cartService.fetchUserCart(userId);
		if (cartItems.isEmpty()) {
			return Optional.empty();
		}
		// Calculate total price
		BigDecimal totalAmount = cartItems.stream()
				.map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		// Create order
		Order order = new Order();
		order.setUserId(userId);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setTotalAmount(totalAmount);
		List<OrderItem> orderItems = cartItems.stream()
				.map(item -> new OrderItem(null, item.getProductId(), item.getQuantity(), item.getPrice(), order))
				.toList();
		order.setOrderItems(orderItems);
		Order savedOrder = orderRepository.save(order);

		// Clear the cart
		cartService.clearCart(userId);

		// Publish order created event
		OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getUserId(),
				savedOrder.getStatus(), savedOrder.getOrderItems().stream().map(item -> {
					return orderItemMapper.toDto(item);
				}).toList(), savedOrder.getTotalAmount(), savedOrder.getCreatedAt());
		rabbitTemplate.convertAndSend(exchangeName, routingKey, event);

		return Optional.of(savedOrder);
	}
}
