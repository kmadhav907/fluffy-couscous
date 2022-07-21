package com.application.shoeApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.shoeApp.model.Category;
import com.application.shoeApp.repository.CategoryRepository;



@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public void addCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	public void removeCategoryById(Integer id) {
		categoryRepository.deleteById(id);
	}
	public Optional<Category> getCategoryById(Integer id) {
		return categoryRepository.findById(id);
	}
}
