package com.malcolm.user.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.malcolm.user.UserRole;
import com.malcolm.user.address.Address;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
	@Id
	private String id; // MongoDb stores IDs as ObjectId type which can't convert to Long so we convert
						// to String
	private String keycloakId;
	private String firstName;
	private String lastName;

	@Indexed(unique = true)
	private String email;
	private String phone;
	private UserRole userRole = UserRole.CUSTOMER;
	private Address address;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	/*
	 * // JPA Entities require a default no-arg constructor. We could still omit
	 * this // since we have lombok. We could use @NoArgsConstructor public User() {
	 * }
	 */

}
