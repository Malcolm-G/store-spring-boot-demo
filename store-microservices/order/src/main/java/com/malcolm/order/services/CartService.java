package com.malcolm.order.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malcolm.order.dto.CartItemRequest;
import com.malcolm.order.dto.ProductDTO;
import com.malcolm.order.dto.UserDTO;
import com.malcolm.order.httpInterface.ProductHttpInterface;
import com.malcolm.order.httpInterface.UserHttpInterface;
import com.malcolm.order.models.CartItem;
import com.malcolm.order.repositories.CartItemRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartItemRepository cartItemRepository;
//	private final UserRepository userRepository;
//	private final ProductRepository productRepository;
	private final ProductHttpInterface productClient;
	private final UserHttpInterface userClient;

	@CircuitBreaker(name = "product", fallbackMethod = "addToCartFallback") // Name is the instance defined in yaml config
	public boolean addToCart(String userId, CartItemRequest request) {

		// Look for product
		Optional<ProductDTO> productOpt = productClient.findById(request.getProductId());

		if (productOpt.isEmpty()) {
			return false;
		}
		ProductDTO product = productOpt.get();

		if (product.getStockQuantity() < request.getQuantity()) {
			return false;
		}

		// Look for user
		Optional<UserDTO> userOpt = userClient.fetchUserbyId(userId);

		if (userOpt.isEmpty()) {
			return false;
		}

		String productId = String.valueOf(request.getProductId());
		CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

		if (existingCartItem != null) {
			// Update Quantity
			existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
			existingCartItem
					.setPrice(BigDecimal.valueOf(1000.00).multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepository.save(existingCartItem);
		} else {
			// Create new cart item
			CartItem cartItem = new CartItem();
			cartItem.setUserId(userId);
			cartItem.setProductId(productId);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(BigDecimal.valueOf(1000.00));
			cartItemRepository.save(cartItem);
		}

		return true;
	}

	public boolean addToCartFallback(String userId, CartItemRequest request, Exception exception) {
		System.out.println("FALLBACK CALLED");
		return false;
	}

	public boolean deleteItemFromCart(String userId, String productId) {
		/*
		 * // Look for product Optional<Product> productOpt =
		 * productRepository.findById(productId);
		 * 
		 * if (productOpt.isEmpty()) { return false; }
		 * 
		 * // Look for user Optional<User> userOpt =
		 * userRepository.findById(Long.valueOf(userId));
		 * 
		 * if (userOpt.isEmpty()) { return false; }
		 * 
		 * return userOpt.flatMap(user -> productOpt.map(product -> {
		 * cartItemRepository.deleteByUserAndProduct(user, product); return true;
		 * })).orElse(false);
		 */
		cartItemRepository.deleteByUserIdAndProductId(userId, productId);
		return true;
	}

	public List<CartItem> fetchUserCart(String userId) {
		/*
		 * // Look for user Optional<User> userOpt =
		 * userRepository.findById(Long.valueOf(userId));
		 * 
		 * if (userOpt.isEmpty()) { return null; } User user = userOpt.get();
		 */

		List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
		return cartItems;
	}

	public void clearCart(String userId) {
		/*
		 * userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::
		 * deleteByUserId);
		 */
		cartItemRepository.deleteByUserId(userId);

	}

}
