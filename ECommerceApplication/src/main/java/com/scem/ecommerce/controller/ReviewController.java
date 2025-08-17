package com.scem.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scem.ecommerce.dto.ReviewRequest;
import com.scem.ecommerce.entity.Review;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.ReviewService;
import com.scem.ecommerce.util.AuthUtil;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    private AuthUtil authUtil;

    public ReviewController(ReviewService reviewService,AuthUtil authUtil) {
        this.reviewService = reviewService;
        this.authUtil = authUtil;
    }

    /** USER Routes**/
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest reviewRequest) {
        User currentUser = authUtil.getCurrentUser(); // Get from SecurityContext
        Review review = reviewService.addReview(
            currentUser.getUserId(),
            reviewRequest.getProductId(),
            reviewRequest.getRating(),
            reviewRequest.getComment()
        );
        return ResponseEntity.ok(review);
    }
   
    @GetMapping
    public ResponseEntity<List<Review>> getReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long userId) {
        if (productId != null) {
            return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
        } else if (userId != null) {
            return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
        } else {
            // Optionally return all reviews or 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        User currentUser = authUtil.getCurrentUser(); 
        reviewService.deleteReview(reviewId, currentUser);
        return ResponseEntity.ok("Review deleted");
    }

}
