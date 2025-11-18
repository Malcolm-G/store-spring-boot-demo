package com.malcolm.store.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malcolm.store.user.dto.UserRequest;
import com.malcolm.store.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private ObjectMapper objectMapper;
	private final UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	public User createUser(UserRequest userRequest) {
		return userRepository.save(userMapper.toUser(userRequest));
	}

	public List<UserResponse> fetchAllUsers() {
		return userRepository.findAll().stream().map(u -> userMapper.toResponse(u)).toList();
	}

	public Optional<UserResponse> fetchUserById(Long id) {
		return userRepository.findById(id).map(userMapper::toResponse);
	}

	/**
	 * Used when I was using the json from the request. Very manual process because
	 * spring normally converts for you. I didn't use DTOs at that time and so this
	 * was my way of working with minimal data.
	 * 
	 * @param id
	 * @param update
	 * @deprecated
	 * @return
	 */
	public User patchUserOld(Long id, JsonNode update) {
		// Get user by the id
		User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			try {
				objectMapper.readerForUpdating(user).readValue(update);
				return userRepository.save(user);
			} catch (IOException e) {
				return null;
			}
		}

		return user;
	}

	public UserResponse patchUser(Long id, UserRequest update) {
		// Get user by the id
		User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			userMapper.updateEntity(update, user);
			return userMapper.toResponse(userRepository.save(user));
		}

		return null;
	}

}
