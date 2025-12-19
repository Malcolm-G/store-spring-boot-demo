package com.malcolm.store.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malcolm.store.cart.CartItem;
import com.malcolm.store.cart.CartService;
import com.malcolm.store.user.User;
import com.malcolm.store.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;
	@Autowired
	private final CartService cartService;
	@Autowired
	private final UserRepository userRepository;

	public Optional<Order> createOrder(String userId) {
		// Validate the user
		Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

		if (userOpt.isEmpty()) {
			return Optional.empty();
		}
		User user = userOpt.get();

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
		order.setUser(user);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setTotalAmount(totalAmount);
		List<OrderItem> orderItems = cartItems.stream()
				.map(item -> new OrderItem(null, item.getProduct(), item.getQuantity(), item.getPrice(), order))
				.toList();
		order.setOrderItems(orderItems);
		Order savedOrder = orderRepository.save(order);

		// Clear the cart
		cartService.clearCart(userId);

		return Optional.of(savedOrder);
	}
}
