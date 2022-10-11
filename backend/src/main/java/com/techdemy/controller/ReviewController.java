package com.techdemy.controller;

import com.techdemy.dto.request.ReviewRequestDto;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.dto.response.ReviewResponseDto;
import com.techdemy.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ApiOperation(value = "Saves review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review posted successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{courseId}")
    public ResponseEntity<GenericResponse> saveReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto,
                                        @PathVariable("courseId") Long courseId) {
        log.info("Got request to save review comment {} and rating", reviewRequestDto.getComment(),
                reviewRequestDto.getRating());
        reviewService.saveReview(reviewRequestDto, courseId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Review posted successfully"));
    }

    @ApiOperation(value = "Fetches review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review fetched successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping(value = "/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable("reviewId") Long reviewId) {
        log.info("Got request to fetch review {}", reviewId);
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    @ApiOperation(value = "Deletes review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review fetched successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "Forbidden to delete review")
    })
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<GenericResponse> deleteReview(@PathVariable("reviewId") Long reviewId) {
        log.info("Got request to delete review {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Review deleted successfully"));
    }

    @ApiOperation(value = "Updates review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review fetched successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "Forbidden to delete review")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{reviewId}")
    public ResponseEntity<GenericResponse> updateReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto ,
                                                        @PathVariable("reviewId") Long reviewId) {
        log.info("Got request to update review {}", reviewId);
        reviewService.updateReview(reviewRequestDto, reviewId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Review updated successfully"));
    }

    @ApiOperation(value = "Fetches all reviews")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review fetched successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping(value = "/{courseId}")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(@PathVariable("courseId") Long courseId) {
        log.info("Got request to fetch all reviews for course {}", courseId);
        return ResponseEntity.ok(reviewService.getAllReviews(courseId));
    }

}
