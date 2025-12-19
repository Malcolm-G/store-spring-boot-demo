package com.malcolm.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.malcolm.order.models.OrderStatus;

import lombok.Data;

@Data
public class OrderResponse {
	private Long id;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private List<OrderItemDTO> orderItems = new ArrayList<>();
	private LocalDateTime createdAt;
}
