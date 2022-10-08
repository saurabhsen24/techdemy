package com.techdemy.service;

import com.techdemy.entities.Review;

import java.util.List;

public interface ReviewService {

    void saveReview(Review review);

    Review getReview(Long reviewId);

    void deleteReview(Long reviewId);

    void update(Review review);

    List<Review> getReviews();

}
