package com.malcolm.order.httpInterface;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.malcolm.order.dto.UserDTO;

@HttpExchange
public interface UserHttpInterface {

	@GetExchange("/api/users/{userId}")
	Optional<UserDTO> fetchUserbyId(@PathVariable String userId);
}
