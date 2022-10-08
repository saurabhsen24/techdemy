package com.techdemy.service.impl;

import com.techdemy.entities.Review;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.ReviewRepository;
import com.techdemy.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review getReview(Long reviewId) {
        Review review = reviewRepository.findById( reviewId ).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return review;
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById( reviewId );
    }

    @Override
    public void update(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

}
