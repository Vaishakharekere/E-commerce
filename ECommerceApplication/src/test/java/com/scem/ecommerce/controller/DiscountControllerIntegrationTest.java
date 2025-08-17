package com.scem.ecommerce.controller;

import com.scem.ecommerce.entity.Discount;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.DiscountService;
import com.scem.ecommerce.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DiscountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    
    @SuppressWarnings("removal")
	@MockBean
    private DiscountService discountService;

    @SuppressWarnings("removal")
	@MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @SuppressWarnings("removal")
	@MockBean
    private JwtTokenUtil jwtTokenUtil;

    private Discount testDiscount;

    @BeforeEach
    void setUp() {
        testDiscount = new Discount();
        testDiscount.setDiscountId(1L);
        testDiscount.setDiscountCode("SAVE10");
        testDiscount.setDiscountValue(10.0); // Assuming numeric value
        testDiscount.setPercentage(true); // field is boolean for isPercentage
        testDiscount.setStartDate(LocalDate.parse("2025-08-01"));
        testDiscount.setEndDate(LocalDate.parse("2025-08-31"));
    }

    @Test
    void testCreateDiscount() throws Exception {
        Long productId = 42L;

        Mockito.when(discountService.createDiscount(
                Mockito.eq(productId),
                Mockito.eq("SAVE10"),
                Mockito.eq(10.0),
                Mockito.eq(true),
                Mockito.eq("2025-08-01"),
                Mockito.eq("2025-08-31")
        )).thenReturn(testDiscount);

        mockMvc.perform(post("/api/discounts/product/" + productId)
                .param("code", "SAVE10")
                .param("value", "10")
                .param("isPercentage", "true")
                .param("startDate", "2025-08-01")
                .param("endDate", "2025-08-31"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.discountCode").value("SAVE10"));
    }

    @Test
    void testListDiscountsByProduct() throws Exception {
        Long productId = 42L;
        List<Discount> discounts = Collections.singletonList(testDiscount);
        Mockito.when(discountService.getDiscountsByProduct(productId)).thenReturn(discounts);

        mockMvc.perform(get("/api/discounts/product/" + productId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].discountCode").value("SAVE10"));
    }

    @Test
    void testGetEffectivePrice() throws Exception {
        Long productId = 42L;
        double basePrice = 100.0;
        Mockito.when(discountService.computeEffectivePrice(productId, basePrice)).thenReturn(90.0);

        mockMvc.perform(get("/api/discounts/product/" + productId + "/price")
                .param("basePrice", "100"))
            .andExpect(status().isOk())
            .andExpect(content().string("90.0"));
    }

    @Test
    void testDeleteDiscount() throws Exception {
        Long discountId = 1L;
        Mockito.doNothing().when(discountService).deleteDiscount(discountId);

        mockMvc.perform(delete("/api/discounts/" + discountId))
            .andExpect(status().isOk())
            .andExpect(content().string("Discount deleted"));
    }
}
