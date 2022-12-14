package com.techdemy.repository;

import com.techdemy.dto.response.ReviewResponseDto;
import com.techdemy.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query(value = "SELECT AVG(rating) FROM reviews where course_id=:courseId", nativeQuery = true)
    Double findAvgRatingByCourseId(@Param("courseId") Long courseId);

    @Query(value = "SELECT r.review_id as reviewId, u.user_name as userName, r.review_comment as reviewComment, " +
            "r.rating as rating FROM reviews r INNER JOIN user u ON " +
            "r.user_id = u.user_id WHERE r.review_id=:reviewId", nativeQuery = true)
    Optional<ReviewResponseDto> getReview(@Param("reviewId") Long reviewId );

    @Modifying
    @Transactional
    @Query(value = "UPDATE reviews SET review_comment=:reviewComment, rating=:rating " +
            "WHERE course_id=:courseId AND user_id=:userId", nativeQuery = true)
    int updateReview(@Param("reviewComment") String reviewComment, @Param("rating") Integer rating,
                      @Param("courseId") Long courseId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reviews WHERE course_id=:courseId AND user_id=:userId", nativeQuery = true)
    int deleteReview(@Param("courseId") Long courseId, @Param("userId") Long userId);

    @Query(value = "SELECT r.review_id as reviewId, u.user_name as userName, r.review_comment as reviewComment, r.rating as rating FROM " +
            "reviews r INNER JOIN user u ON r.user_id = u.user_id WHERE r.course_id=:courseId", nativeQuery = true)
    List<ReviewResponseDto> getAllReviews(@Param("courseId") Long courseId );

}
