package com.malcolm.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.malcolm.user.models.User;

public interface UserRepository extends MongoRepository<User, String> {

}
