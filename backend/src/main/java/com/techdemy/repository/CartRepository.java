package com.techdemy.repository;

import com.techdemy.dto.response.CartDto;
import com.techdemy.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart WHERE user_id=:userId AND course_id=:courseId" , nativeQuery = true)
    void deleteByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query(value = "SELECT c.course_id as courseId,c.course_name as courseName,c.course_description as courseDescription," +
            "c.course_price as coursePrice, c.author as author FROM courses c INNER JOIN cart ct ON c.course_id = ct.course_id " +
            "WHERE ct.user_id=:userId",nativeQuery = true)
    List<CartDto> getAllCartItems( @Param("userId") Long userId );

    @Query(value = "SELECT COUNT(*) FROM cart WHERE user_id=:userId", nativeQuery = true)
    Integer getCartCount(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart WHERE user_id=:userId AND course_id IN (:courseIds)", nativeQuery = true)
    void deleteAllByUserIdAndCourses(@Param("userId") Long userId,@Param("courseIds") List<Long> courseIds);

}
