package com.malcolm.store.user.dto;

import com.malcolm.store.address.Address;

import lombok.Data;

@Data
public class UserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Address address;
}
