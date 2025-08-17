package com.scem.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.CategoryRepository;
import com.scem.ecommerce.entity.Category;
import com.scem.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryRepository categoryRepo;

   
    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category addCategory(Category category) {
        Category saved = categoryRepo.save(category);
        logger.info("Added new category: {}", saved.getCategoryName());
        return saved;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        logger.debug("Fetching category by id: {}", id);
        return categoryRepo.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        logger.debug("Fetching all categories");
        return (List<Category>) categoryRepo.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        logger.info("Updating category id {} with new name '{}'", id, updatedCategory.getCategoryName());
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepo.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("Deleting category with id: {}", id);
        categoryRepo.deleteById(id);
    }
}
