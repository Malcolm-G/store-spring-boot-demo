package com.malcolm.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malcolm.order.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
