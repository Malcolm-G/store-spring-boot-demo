package com.malcolm.order.httpInterface;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.malcolm.order.dto.ProductDTO;

@HttpExchange
public interface ProductHttpInterface {

	@GetExchange("/api/products/{productId}")
	Optional<ProductDTO> findById(@PathVariable Long productId);
}
