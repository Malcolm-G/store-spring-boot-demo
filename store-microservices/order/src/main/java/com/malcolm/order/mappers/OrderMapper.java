package com.malcolm.order.mappers;

import org.mapstruct.Mapper;

import com.malcolm.order.dto.OrderResponse;
import com.malcolm.order.models.Order;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class })
public interface OrderMapper {

	OrderResponse toResponse(Order order);
}
