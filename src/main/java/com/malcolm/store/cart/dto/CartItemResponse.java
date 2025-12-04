package com.malcolm.store.cart.dto;

import java.math.BigDecimal;

import com.malcolm.store.product.dto.ProductResponse;

import lombok.Data;

@Data
public class CartItemResponse {
	private ProductResponse product;
	private Integer quantity;
	private BigDecimal price;
}
