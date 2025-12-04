package com.malcolm.store.cart;

import org.mapstruct.Mapper;

import com.malcolm.store.cart.dto.CartItemResponse;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	CartItemResponse toResponse(CartItem item);

}
