package com.malcolm.store.order;

import org.mapstruct.Mapper;

import com.malcolm.store.order.dto.OrderResponse;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class })
public interface OrderMapper {

	OrderResponse toResponse(Order order);
}
