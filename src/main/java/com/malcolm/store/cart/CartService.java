package com.malcolm.store.cart;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malcolm.store.cart.dto.CartItemRequest;
import com.malcolm.store.product.Product;
import com.malcolm.store.product.ProductRepository;
import com.malcolm.store.user.User;
import com.malcolm.store.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public boolean addToCart(String userId, CartItemRequest request) {
		// Look for product
		Optional<Product> productOpt = productRepository.findById(request.getProductId());

		if (productOpt.isEmpty()) {
			return false;
		}
		Product product = productOpt.get();

		if (product.getStockQuantity() < request.getQuantity()) {
			return false;
		}

		// Look for user
		Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

		if (userOpt.isEmpty()) {
			return false;
		}

		User user = userOpt.get();

		CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

		if (existingCartItem != null) {
			// Update Quantity
			existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
			existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepository.save(existingCartItem);
		} else {
			// Create new cart item
			CartItem cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
			cartItemRepository.save(cartItem);
		}

		return true;
	}

	public boolean deleteItemFromCart(String userId, Long productId) {
		// Look for product
		Optional<Product> productOpt = productRepository.findById(productId);

		if (productOpt.isEmpty()) {
			return false;
		}

		// Look for user
		Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

		if (userOpt.isEmpty()) {
			return false;
		}

		return userOpt.flatMap(user ->
			productOpt.map(product -> {
				cartItemRepository.deleteByUserAndProduct(user, product);
				return true;
			})
		).orElse(false);
	}

}
