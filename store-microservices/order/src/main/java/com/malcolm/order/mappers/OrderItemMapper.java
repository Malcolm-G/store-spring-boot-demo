package com.malcolm.order.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.malcolm.order.dto.OrderItemDTO;
import com.malcolm.order.models.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	@Mapping(target = "subTotal", expression = "java(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))")
	OrderItemDTO toDto(OrderItem item);
}
