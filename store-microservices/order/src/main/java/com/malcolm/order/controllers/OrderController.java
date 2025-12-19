package com.malcolm.order.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malcolm.order.dto.OrderResponse;
import com.malcolm.order.mappers.OrderMapper;
import com.malcolm.order.models.Order;
import com.malcolm.order.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private final OrderService orderService;
	@Autowired
	private final OrderMapper orderMapper;

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestHeader("x-user-id") String userId) {

		Optional<Order> orderOpt = orderService.createOrder(userId);
		if (orderOpt.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		Order order = orderOpt.get();
		OrderResponse response = orderMapper.toResponse(order);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
