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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private final ProductService productService;
	@Autowired
	private final ProductMapper mapper;

	@PostMapping
	public ResponseEntity<ProductResponse> createProdcut(@RequestBody ProductRequest request) {
		Product product = productService.createProduct(mapper.toProduct(request));
		return ResponseEntity.ok(mapper.toResponse(product));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
		List<ProductResponse> response = productService.getAllProducts().stream().map(p -> mapper.toResponse(p))
				.toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/active")
	public ResponseEntity<List<ProductResponse>> getAllActiveProducts() {
		List<ProductResponse> response = productService.getAllActiveProducts().stream().map(p -> mapper.toResponse(p))
				.toList();
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
		Product product = productService.updateProduct(id, request);
		return product != null ? ResponseEntity.ok(mapper.toResponse(product)) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		boolean deleted = productService.deleteProduct(id);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
		return ResponseEntity
				.ok(productService.searchProducts(keyword).stream().map(p -> mapper.toResponse(p)).toList());
	}

}
