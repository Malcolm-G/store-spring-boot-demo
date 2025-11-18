
package com.malcolm.store.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malcolm.store.user.dto.UserRequest;
import com.malcolm.store.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.fetchAllUsers());
	}

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
		userService.createUser(request);
		return ResponseEntity.ok("User created successfully");
	}

	/**
	 * 
	 * @param id Note that PathVariable automatically links id to the {id} in the
	 *           path because they have the same name. Otherwise we would have to
	 *           set: <br>
	 *           <code>@PathVariable("id") Long id</code>
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<UserResponse> fetchUserbyId(@PathVariable Long id) {
		return userService.fetchUserById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponse> putMethodName(@PathVariable Long id, @RequestBody UserRequest request) {
		UserResponse updatedUser = userService.patchUser(id, request);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		}
		return ResponseEntity.badRequest().build();
	}

}
