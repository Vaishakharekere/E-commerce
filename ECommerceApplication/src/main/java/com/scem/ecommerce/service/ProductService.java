package com.scem.ecommerce.service;

import java.util.Optional;
import java.util.List;
import com.scem.ecommerce.entity.Product;

public interface ProductService {
	
	Product addProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);

}
