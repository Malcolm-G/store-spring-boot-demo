package com.malcolm.store.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	private AtomicLong currentId = new AtomicLong(0);
	List<User> userList = new ArrayList<>();

	public List<User> createUser(User user) {
		long newId = currentId.addAndGet(1);
		user.setId(newId);
		userList.add(user);
		return userList;
	}
	
	public List<User> fetchAllUsers(){
		return userList;
	}

}
