package com.techdemy.service.impl;

import com.techdemy.dto.request.ReviewRequestDto;
import com.techdemy.dto.response.ReviewResponseDto;
import com.techdemy.entities.Course;
import com.techdemy.entities.Review;
import com.techdemy.entities.User;
import com.techdemy.exception.BadRequestException;
import com.techdemy.exception.ForbiddenResourceException;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CourseRepository;
import com.techdemy.repository.ReviewRepository;
import com.techdemy.security.JwtHelper;
import com.techdemy.service.CourseService;
import com.techdemy.service.ReviewService;
import com.techdemy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void saveReview(ReviewRequestDto reviewRequestDto, Long courseId) {
        log.debug("Saving review {} in db for course {}", reviewRequestDto.getComment(), courseId);

        User user = userService.getUserByUserName(JwtHelper.getCurrentLoggedInUsername());
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found"));

        Review review = Review.builder()
                .reviewComment(reviewRequestDto.getComment())
                .rating(reviewRequestDto.getRating())
                .course(course)
                .user(user)
                .build();

        reviewRepository.save(review);

        double avgRating = reviewRepository.findAvgRatingByCourseId(courseId);

        log.debug("Average rating of course {} is {}", courseId, avgRating);

        course.setCourseRating(avgRating);
        courseRepository.save(course);

    }

    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        log.debug("Fetches review comment {}", reviewId);
        ReviewResponseDto reviewResponseDto = reviewRepository.getReview(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review comment not found"));
        return reviewResponseDto;
    }

    @Override
    @Transactional
    public void deleteReview(Long courseId) {
        log.debug("Deleting review comment for course {}", courseId);
        Long userId = Long.parseLong(JwtHelper.getCurrentLoggedInUserId());

        int numberOfDeletedRecords = reviewRepository.deleteReview(courseId , userId);

        if( numberOfDeletedRecords == 0 ) {
            throw new ForbiddenResourceException("You are forbidden to delete this review");
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found"));

        Double avgRating = reviewRepository.findAvgRatingByCourseId(courseId);

        avgRating = Objects.isNull( avgRating ) ? 0 : avgRating;

        log.debug("Average rating of course {} is {}", courseId, avgRating);

        course.setCourseRating(avgRating);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void updateReview(ReviewRequestDto reviewRequestDto, Long courseId) {
        log.debug("Updating review comment for course {}", courseId);
        Long userId = Long.parseLong(JwtHelper.getCurrentLoggedInUserId());
        int numberOfUpdatedRecords = reviewRepository.updateReview(reviewRequestDto.getComment(),
                reviewRequestDto.getRating(), courseId, userId);

        if( numberOfUpdatedRecords == 0 ) {
            throw new ForbiddenResourceException("You are forbidden to update this review");
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found"));
        Double avgRating = reviewRepository.findAvgRatingByCourseId(courseId);

        log.debug("Average rating of course {} is {}", courseId, avgRating);

        course.setCourseRating(avgRating);
        courseRepository.save(course);
    }

    @Override
    public List<ReviewResponseDto> getAllReviews(Long courseId) {
        log.debug("Fetches all reviews for course {}", courseId);
        return reviewRepository.getAllReviews(courseId);
    }

}
