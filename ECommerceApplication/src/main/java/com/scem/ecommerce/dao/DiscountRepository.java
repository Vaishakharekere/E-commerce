package com.scem.ecommerce.dao;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scem.ecommerce.entity.Discount;
import com.scem.ecommerce.entity.Product;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
	List<Discount> findByProduct(Product product);
    List<Discount> findByProductAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Product product, LocalDate start, LocalDate end);

}
