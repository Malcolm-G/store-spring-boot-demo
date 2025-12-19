package com.malcolm.order.mappers;

import org.mapstruct.Mapper;

import com.malcolm.order.dto.CartItemResponse;
import com.malcolm.order.models.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	CartItemResponse toResponse(CartItem item);

}
