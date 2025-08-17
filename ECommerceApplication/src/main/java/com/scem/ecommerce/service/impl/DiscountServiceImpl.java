package com.scem.ecommerce.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.DiscountRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.entity.Discount;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.exception.ProductNotFoundException;
import com.scem.ecommerce.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);
    @Autowired
    private DiscountRepository discountRepo;
    @Autowired
    private ProductRepository productRepo;

    
    public DiscountServiceImpl(DiscountRepository discountRepo, ProductRepository productRepo) {
        this.discountRepo = discountRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Discount createDiscount(Long productId, String code, double value, boolean isPercentage, String startDate,
            String endDate) {
        if (value <= 0)
            throw new IllegalArgumentException("Discount value must be > 0");
        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Discount discount = new Discount();
        discount.setDiscountCode(code);
        discount.setDiscountValue(value);
        discount.setPercentage(isPercentage);
        discount.setStartDate(start);
        discount.setEndDate(end);
        discount.setProduct(product);

        Discount saved = discountRepo.save(discount);
        logger.info("Created discount '{}'- for product '{}' with value: {} (isPercentage: {})", code,
                product.getName(), value, isPercentage);
        return saved;
    }

    @Override
    public List<Discount> getDiscountsByProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        logger.debug("Fetching discounts for product '{}'", product.getName());
        return discountRepo.findByProduct(product);
    }

    @Override
    public double computeEffectivePrice(Long productId, double basePrice) {
        LocalDate today = LocalDate.now();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        List<Discount> active = discountRepo.findByProductAndStartDateLessThanEqualAndEndDateGreaterThanEqual(product,
                today, today);

        Discount best = active.stream().max(Comparator.comparingDouble(d -> {
            if (d.isPercentage()) {
                return basePrice * (d.getDiscountValue() / 100.0);
            } else {
                return d.getDiscountValue();
            }
        })).orElse(null);

        if (best == null) {
            logger.debug("No active discount for product '{}'. Returning base price {}", product.getName(), basePrice);
            return basePrice;
        }

        double discountAmount = best.isPercentage() ? basePrice * (best.getDiscountValue() / 100.0)
                : best.getDiscountValue();

        double finalPrice = Math.max(0, basePrice - discountAmount);
        logger.debug("Computed effective price for product '{}': base={} discountAmount={} final={}", product.getName(),
                basePrice, discountAmount, finalPrice);
        return finalPrice;
    }

    @Override
    public void deleteDiscount(Long discountId) {
        discountRepo.deleteById(discountId);
        logger.info("Deleted discount with id {}", discountId);
    }
}
