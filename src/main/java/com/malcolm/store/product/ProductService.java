package com.malcolm.store.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malcolm.store.product.dto.ProductRequest;
import com.malcolm.store.product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Autowired
	private final ProductMapper mapper;
	@Autowired
	private final ProductRepository repository;

	public ProductResponse createProduct(ProductRequest request) {
		Product product = mapper.toProduct(request);
		return mapper.toResponse(repository.save(product));
	}

	public List<ProductResponse> getAllProducts() {
		return repository.findAll().stream().map(p -> mapper.toResponse(p)).toList();
	}

	public ProductResponse updateProduct(Long id, ProductRequest request) {
		return mapper.toResponse(repository.findById(id).orElse(null));
	}

	public boolean deleteProduct(Long id) {
		return repository.findById(id).map(p -> {
			p.setActive(false);
			return true;
		}).orElse(false);
	}

}
