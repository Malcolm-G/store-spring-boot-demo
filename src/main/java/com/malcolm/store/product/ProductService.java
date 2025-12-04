package com.malcolm.store.product;

import java.util.Collection;
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
	private final ProductRepository repository;

	public Product createProduct(Product product) {
		return repository.save(product);
	}

	public List<Product> getAllProducts() {
		return repository.findAll();
	}

	public Product updateProduct(Long id, ProductRequest request) {
		return repository.findById(id).orElse(null);
	}

	public boolean deleteProduct(Long id) {
		return repository.findById(id).map(p -> {
			p.setActive(false);
			repository.save(p);
			return true;
		}).orElse(false);
	}

	public List<Product> getAllActiveProducts() {
		return repository.findByActiveTrue();
	}

	public List<Product> searchProducts(String keyword) {
		return repository.searchProducts(keyword);
	}

}
