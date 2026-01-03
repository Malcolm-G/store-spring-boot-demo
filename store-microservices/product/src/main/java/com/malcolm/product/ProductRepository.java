package com.malcolm.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// JPA can infer based on the method name. It sees "find by", "Attribute -
	// 'active'", "Attribute value - 'true'"
	List<Product> findByActiveTrue();

	@Query("SELECT p from STO_Product p WHERE p.active = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Product> searchProducts(@Param("keyword") String keyword);

	Optional<Product> findByIdAndActiveTrue(Long productId);

}
