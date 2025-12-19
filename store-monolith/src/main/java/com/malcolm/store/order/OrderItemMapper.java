package com.malcolm.store.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.malcolm.store.order.dto.OrderItemDTO;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	@Mapping(source = "product.id", target = "productId")
	@Mapping(target = "subTotal", expression = "java(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))")
	OrderItemDTO toDto(OrderItem item);
}
