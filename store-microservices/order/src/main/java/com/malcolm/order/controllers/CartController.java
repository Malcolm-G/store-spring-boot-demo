package com.malcolm.order.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malcolm.order.dto.CartItemRequest;
import com.malcolm.order.dto.CartItemResponse;
import com.malcolm.order.mappers.CartItemMapper;
import com.malcolm.order.models.CartItem;
import com.malcolm.order.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private final CartService cartService;
	@Autowired
	private final CartItemMapper cartItemMapper;

	@PostMapping
	public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
			@RequestBody CartItemRequest request) {
		if (!cartService.addToCart(userId, request)) {
			return ResponseEntity.badRequest().body(
					"Not able to complete the request | Product Out of Stock or User not found or Product Not Found");
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/items/{productId}")
	public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,
			@PathVariable Long productId) {

		boolean deleted = cartService.deleteItemFromCart(userId, String.valueOf(productId));
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

	}

	@GetMapping
	public ResponseEntity<List<CartItemResponse>> fetchUserCart(@RequestHeader("x-user-id") String userId) {
		List<CartItem> cartItems = cartService.fetchUserCart(userId);

		if (cartItems == null) {
			return ResponseEntity.notFound().build();
		}

		if (cartItems.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<CartItemResponse> response = cartItems.stream().map(item -> cartItemMapper.toResponse(item)).toList();

		return ResponseEntity.ok(response);
	}

}
