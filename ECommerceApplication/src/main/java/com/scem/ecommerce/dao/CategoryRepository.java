package com.scem.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scem.ecommerce.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
