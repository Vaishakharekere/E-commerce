package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.DiscountRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.entity.Discount;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.service.impl.DiscountServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

class DiscountServiceTest {

    @Mock private DiscountRepository discountRepo;
    @Mock private ProductRepository productRepo;

    @InjectMocks private DiscountServiceImpl discountService;

    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setProductId(1L);
        product.setName("Test Product");
    }

    @Test
    void testCreateDiscount() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(discountRepo.save(any(Discount.class))).thenAnswer(inv -> inv.getArgument(0));
        Discount d = discountService.createDiscount(1L, "CODE123", 20.0, false, "2025-08-01", "2025-08-31");
        assertEquals("CODE123", d.getDiscountCode());
        assertFalse(d.isPercentage());
    }

    @Test
    void testGetDiscountsByProduct() {
        List<Discount> list = new ArrayList<>();
        list.add(new Discount());
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(discountRepo.findByProduct(product)).thenReturn(list);
        List<Discount> discounts = discountService.getDiscountsByProduct(1L);
        assertEquals(1, discounts.size());
    }

    @Test
    void testComputeEffectivePrice_NoDiscounts() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(discountRepo.findByProductAndStartDateLessThanEqualAndEndDateGreaterThanEqual(any(), any(), any()))
            .thenReturn(new ArrayList<>());
        double price = discountService.computeEffectivePrice(1L, 100.0);
        assertEquals(100.0, price);
    }
}
