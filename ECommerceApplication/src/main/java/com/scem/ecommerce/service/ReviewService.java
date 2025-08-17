package com.scem.ecommerce.service;

import java.util.List;

import com.scem.ecommerce.entity.Review;
import com.scem.ecommerce.entity.User;

public interface ReviewService {
	Review addReview(Long userId, Long productId, int rating, String comment);
    List<Review> getReviewsByProduct(Long productId);
    List<Review> getReviewsByUser(Long userId);
    void deleteReview(Long reviewId, User currentUser);

}
