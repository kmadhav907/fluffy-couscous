package com.application.shoeApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.shoeApp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByEmail(String email);
}
