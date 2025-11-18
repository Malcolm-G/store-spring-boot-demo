package com.malcolm.store.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.malcolm.store.user.dto.UserRequest;
import com.malcolm.store.user.dto.UserResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserResponse toResponse(User user);

	User toUser(UserRequest request);

	void updateEntity(UserRequest request, @MappingTarget User user);
}
