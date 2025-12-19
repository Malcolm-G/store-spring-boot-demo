package com.malcolm.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.malcolm.user.dto.UserRequest;
import com.malcolm.user.dto.UserResponse;
import com.malcolm.user.models.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserResponse toResponse(User user);

	User toUser(UserRequest request);

	void updateEntity(UserRequest request, @MappingTarget User user);
}
