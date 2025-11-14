
package com.malcolm.store.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public List<User> getAllUsers() {
		return userService.fetchAllUsers();
	}
	
	
	@PostMapping("/api/user")
	public String createUser(@RequestBody User user) {
		userService.createUser(user);
		return "User created successfully";
	}
}
