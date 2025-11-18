package com.malcolm.store.user.dto;

import java.time.LocalDateTime;

import com.malcolm.store.address.dto.AddressDTO;
import com.malcolm.store.admin.UserRole;

import lombok.Data;

@Data
public class UserResponse {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private UserRole userRole;
	private AddressDTO address;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
