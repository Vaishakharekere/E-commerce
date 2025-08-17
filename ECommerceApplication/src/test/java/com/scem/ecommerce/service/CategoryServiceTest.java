package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.CategoryRepository;
import com.scem.ecommerce.entity.Category;
import com.scem.ecommerce.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class CategoryServiceTest {

    @Mock private CategoryRepository categoryRepo;
    @InjectMocks private CategoryServiceImpl categoryService;
    private Category category;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
    }

    @Test
    void testAddCategory() {
        when(categoryRepo.save(category)).thenReturn(category);
        Category saved = categoryService.addCategory(category);
        assertEquals("Electronics", saved.getCategoryName());
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
        Optional<Category> found = categoryService.getCategoryById(1L);
        assertTrue(found.isPresent());
        assertEquals("Electronics", found.get().getCategoryName());
    }

    @Test
    void testGetAllCategories() {
        List<Category> list = new ArrayList<>();
        list.add(category);
        when(categoryRepo.findAll()).thenReturn(list);
        List<Category> all = categoryService.getAllCategories();
        assertEquals(1, all.size());
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepo).deleteById(1L);
        categoryService.deleteCategory(1L);
        verify(categoryRepo, times(1)).deleteById(1L);
    }
}
