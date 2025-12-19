package com.malcolm.order.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemResponse {
	private String productId;
	private Integer quantity;
	private BigDecimal price;
}
