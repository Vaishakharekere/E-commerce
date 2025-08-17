package com.scem.ecommerce.service;

import java.util.List;

import com.scem.ecommerce.entity.Discount;

public interface DiscountService {
	
	Discount createDiscount(Long productId, String code, double value, boolean isPercentage,
            String startDate, String endDate);
	List<Discount> getDiscountsByProduct(Long productId);
	double computeEffectivePrice(Long productId, double basePrice);
	void deleteDiscount(Long discountId);

}
