package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setProductId(1L);
        product.setName("Laptop");
        product.setPrice(1200.0);
        product.setDiscount(100.0);
    }

    @Test
    void testAddProduct() {
        when(productRepo.save(product)).thenReturn(product);
        Product saved = productService.addProduct(product);
        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
    }

    @Test
    void testGetProductById() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> found = productService.getProductById(1L);
        assertTrue(found.isPresent());
        assertEquals(1200.0, found.get().getPrice());
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepo.findAll()).thenReturn(products);
        List<Product> list = productService.getAllProducts();
        assertEquals(1, list.size());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepo).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepo, times(1)).deleteById(1L);
    }
}
