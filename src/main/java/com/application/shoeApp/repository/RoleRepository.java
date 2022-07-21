package com.application.shoeApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.shoeApp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	

}
