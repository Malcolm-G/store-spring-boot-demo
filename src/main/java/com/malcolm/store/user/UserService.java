package com.malcolm.store.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Autowired
	private ObjectMapper objectMapper;

	private AtomicLong currentId = new AtomicLong(0);
	private List<User> userList = new ArrayList<>();

	public List<User> createUser(User user) {
		long newId = currentId.addAndGet(1);
		user.setId(newId);
		userList.add(user);
		return userList;
	}

	public List<User> fetchAllUsers() {
		return userList;
	}

	public Optional<User> fetchUserById(Long id) {
		if (id != null) {
			return userList.stream().filter(u -> id.equals(u.getId())).findFirst();
		}
		return Optional.ofNullable(null);
	}

	public User patchUser(Long id, JsonNode update) {
		// Get user by the id
		User user = userList.stream().filter(u -> Long.valueOf(u.getId()) != null && id.equals(u.getId())).findFirst()
				.orElse(null);

		if (user != null) {
			try {
				objectMapper.readerForUpdating(user).readValue(update);
			} catch (IOException e) {
				return null;
			}
		}

		return user;
	}

}
