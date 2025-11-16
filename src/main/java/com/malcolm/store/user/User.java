package com.malcolm.store.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "STO_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;

	// JPA Entities require a default no-arg constructor. We could still omit this
	// since we have lombok. We could use @NoArgsConstructor
	public User() {
	}

}
