
package com.malcolm.store.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private final UserService userService;

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.fetchAllUsers());
	}

	@PostMapping("/api/user")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.ok("User created successfully");
	}

	@GetMapping("/api/user/{id}")
	public ResponseEntity<User> fetchUserbyId(@PathVariable Long id) {
		return userService.fetchUserById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}
}
