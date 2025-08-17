package com.scem.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scem.ecommerce.dto.ReviewRequest;
import com.scem.ecommerce.entity.Review;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.ReviewService;
import com.scem.ecommerce.util.AuthUtil;
import com.scem.ecommerce.util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    ReviewService reviewService;
    @MockBean
    AuthUtil authUtil; // Because ReviewController uses AuthUtil

    ObjectMapper mapper = new ObjectMapper();
    Review review;

    @BeforeEach
    void setup() {
        review = new Review();
        review.setReviewId(1L);
        review.setRating(5);
        review.setComment("Excellent!");
    }

    @Test
    void testAddReview() throws Exception {
        ReviewRequest req = new ReviewRequest();
        req.setProductId(2L);
        req.setUserId(1L);
        req.setRating(5);
        req.setComment("Excellent!");

        User mockUser = new User();
        mockUser.setUserId(1L);
        Mockito.when(authUtil.getCurrentUser()).thenReturn(mockUser);
        Mockito.when(reviewService.addReview(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void testGetReviewsByProduct() throws Exception {
        Mockito.when(reviewService.getReviewsByProduct(2L)).thenReturn(Collections.singletonList(review));
        mockMvc.perform(get("/api/reviews?productId=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewId").value(1L));
    }
}
