package com.malcolm.store.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private ObjectMapper objectMapper;
	private final UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public List<User> fetchAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> fetchUserById(Long id) {
		return userRepository.findById(id);
	}

	public User patchUser(Long id, JsonNode update) {
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

}
