package com.malcolm.user.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malcolm.user.UserMapper;
import com.malcolm.user.dto.UserRequest;
import com.malcolm.user.dto.UserResponse;
import com.malcolm.user.models.User;
import com.malcolm.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final KeycloakAdminService keyCloakAdminService;
	private final UserMapper userMapper;

	public User createUser(UserRequest userRequest) {
		String token = keyCloakAdminService.getAdminAccessToken();
		String keycloakUserId = keyCloakAdminService.createUser(token, userRequest);
		User user = userMapper.toUser(userRequest);
		user.setKeycloakId(keycloakUserId);
		keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(), "USER", keycloakUserId);
		return userRepository.save(user);
	}

	public List<UserResponse> fetchAllUsers() {
		return userRepository.findAll().stream().map(u -> userMapper.toResponse(u)).toList();
	}

	public Optional<UserResponse> fetchUserById(String id) {
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
	public User patchUserOld(String id, JsonNode update) {
		// Get user by the id
		User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			try {
				objectMapper.readerForUpdating(user).readValue(update);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return userRepository.save(user);
		}

		return user;
	}

	public UserResponse patchUser(String id, UserRequest update) {
		// Get user by the id
		User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			userMapper.updateEntity(update, user);
			log.info("User with id: {} has been updated", user.getId());
			return userMapper.toResponse(userRepository.save(user));
		}

		return null;
	}

}
