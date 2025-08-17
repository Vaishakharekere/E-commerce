package com.scem.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.dao.ReviewRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.Review;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.entity.enums.RoleName;
import com.scem.ecommerce.exception.ProductNotFoundException;
import com.scem.ecommerce.exception.ReviewNotFoundException;
import com.scem.ecommerce.exception.UnauthorizedActionException;
import com.scem.ecommerce.exception.UserNotFoundException;
import com.scem.ecommerce.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private UserRepository userRepo;

    public ReviewServiceImpl(ReviewRepository reviewRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.reviewRepo = reviewRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Review addReview(Long userId, Long productId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewdate(LocalDateTime.now());

        Review saved = reviewRepo.save(review);
        logger.info("User [{}] added review with rating {} for product [{}]", userId, rating, productId);
        return saved;
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        logger.debug("Fetching reviews for product [{}]", productId);
        return reviewRepo.findByProduct(product);
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        logger.debug("Fetching reviews by user [{}]", userId);
        return reviewRepo.findByUser(user);
    }

    @Override
    public void deleteReview(Long reviewId, User currentUser) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        if (currentUser.hasRole(RoleName.ROLE_ADMIN) || review.getUser().getUserId().equals(currentUser.getUserId())) {
            reviewRepo.delete(review);
            logger.info("Review [{}] deleted by user [{}]", reviewId, currentUser.getUserId());
        } else {
            logger.warn("Unauthorized attempt to delete review [{}] by user [{}]", reviewId, currentUser.getUserId());
            throw new UnauthorizedActionException("Not authorized to delete this review");
        }
    }
}
