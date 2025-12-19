package com.malcolm.user.dto;

import com.malcolm.user.address.Address;

import lombok.Data;

@Data
public class UserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Address address;
}
