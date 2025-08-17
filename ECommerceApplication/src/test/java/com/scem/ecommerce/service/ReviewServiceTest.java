package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.ReviewRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Review;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.impl.ReviewServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepo;
    @Mock
    private ProductRepository productRepo;
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User user;
    private Product product;
    private Review review;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        product = new Product();
        product.setProductId(1L);
        review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(5);
    }

    @Test
    void testAddReview() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepo.save(any(Review.class))).thenReturn(review);

        Review saved = reviewService.addReview(1L, 1L, 5, "Great!");
        assertEquals(5, saved.getRating());
    }

    @Test
    void testGetReviewsByProduct() {
        List<Review> list = new ArrayList<>();
        list.add(review);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepo.findByProduct(product)).thenReturn(list);

        List<Review> reviews = reviewService.getReviewsByProduct(1L);
        assertEquals(1, reviews.size());
    }
}
