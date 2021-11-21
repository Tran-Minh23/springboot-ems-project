package com.springboot.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ems.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
