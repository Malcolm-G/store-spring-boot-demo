package com.malcolm.user.dto;

import java.time.LocalDateTime;

import com.malcolm.user.UserRole;
import com.malcolm.user.address.dto.AddressDTO;

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
