package com.scem.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.scem.ecommerce.entity.Category;

public interface CategoryService {
	
	Category addCategory(Category category);
	Optional<Category> getCategoryById(Long id);
	List<Category> getAllCategories();
	Category updateCategory(Long id, Category updatedCategory);
    void deleteCategory(Long id);
} 
