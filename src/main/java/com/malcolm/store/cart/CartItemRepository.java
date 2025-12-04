package com.malcolm.store.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malcolm.store.product.Product;
import com.malcolm.store.user.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	CartItem findByUserAndProduct(User user, Product product);

	void deleteByUserAndProduct(User user, Product product);

}
