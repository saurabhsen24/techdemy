package com.techdemy.service;

import com.techdemy.dto.request.ReviewRequestDto;
import com.techdemy.dto.response.ReviewResponseDto;
import com.techdemy.entities.Review;

import java.util.List;

public interface ReviewService {

    void saveReview(ReviewRequestDto reviewRequestDto, Long courseId);

    ReviewResponseDto getReview(Long reviewId);

    void deleteReview(Long courseId);

    void updateReview(ReviewRequestDto reviewRequestDto, Long reviewId);

    List<ReviewResponseDto> getAllReviews(Long courseId);

}
