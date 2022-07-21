package com.application.shoeApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.shoeApp.model.Category;

public interface CategoryRepository extends JpaRepository<Category , Integer> {

}
