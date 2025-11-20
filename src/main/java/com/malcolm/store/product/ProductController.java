package com.malcolm.store.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malcolm.store.product.dto.ProductRequest;
import com.malcolm.store.product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prodcuts")
public class ProductController {

	@Autowired
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProdcut(@RequestBody ProductRequest productRequest) {
		return ResponseEntity.ok(productService.createProduct(productRequest));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
		ProductResponse response = productService.updateProduct(id, request);
		return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(Long id) {
		boolean deleted = productService.deleteProduct(id);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
