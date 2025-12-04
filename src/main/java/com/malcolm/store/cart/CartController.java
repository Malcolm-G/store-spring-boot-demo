package com.malcolm.store.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malcolm.store.cart.dto.CartItemRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private final CartService cartService;

	@PostMapping
	public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
			@RequestBody CartItemRequest request) {
		if (!cartService.addToCart(userId, request)) {
			return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product Not Found");
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/items/{productId}")
	public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,
			@PathVariable Long productId) {

		boolean deleted = cartService.deleteItemFromCart(userId, productId);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

	}

}
