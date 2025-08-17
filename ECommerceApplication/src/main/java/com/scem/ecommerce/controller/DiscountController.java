package com.scem.ecommerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.scem.ecommerce.entity.Discount;
import com.scem.ecommerce.service.DiscountService;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    /** Admin Routes : Create discount for a product  **/
    @PostMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Discount> create(
            @PathVariable Long productId,
            @RequestParam String code,
            @RequestParam double value,
            @RequestParam boolean isPercentage,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return ResponseEntity.ok(
                discountService.createDiscount(productId, code, value, isPercentage, startDate, endDate)
        );
    }

    /** Public Routes: Get all discounts for a product  **/
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<Discount>> listByProduct(@PathVariable Long productId) {

        List<Discount> discount = discountService.getDiscountsByProduct(productId);
        return ResponseEntity.ok(discount);

    }

    /** Public Routes: Get effective price for a product  **/
    @GetMapping("/product/{productId}/price")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Double> getEffectivePrice(
            @PathVariable Long productId,
            @RequestParam double basePrice) {

        return ResponseEntity.ok(discountService.computeEffectivePrice(productId, basePrice));
    }

    // Admin Routes : Delete a discount 
    @DeleteMapping("/{discountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long discountId) {

        discountService.deleteDiscount(discountId);
        return ResponseEntity.ok("Discount deleted");
    }
}
