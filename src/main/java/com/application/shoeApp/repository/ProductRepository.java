package com.application.shoeApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.application.shoeApp.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByCategory_Id(Integer id);
}
