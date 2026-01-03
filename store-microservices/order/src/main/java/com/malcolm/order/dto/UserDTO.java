package com.malcolm.order.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private AddressDTO address;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
