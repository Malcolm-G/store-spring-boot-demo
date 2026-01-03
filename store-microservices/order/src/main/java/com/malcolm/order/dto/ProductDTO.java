package com.malcolm.order.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	private String name;
	private String description;
	private BigDecimal price;
	private Integer stockQuantity;
	private String category;
	private String imageUrl;
	private String active;
}
