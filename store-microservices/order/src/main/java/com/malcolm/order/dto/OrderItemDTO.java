package com.malcolm.order.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Long id;
	private String productId;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal subTotal;
}
