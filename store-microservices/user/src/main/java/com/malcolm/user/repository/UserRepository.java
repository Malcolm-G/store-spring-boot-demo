package com.malcolm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malcolm.user.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
