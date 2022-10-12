package com.techdemy.repository;

import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByCategory( String category );

    @Modifying
    @Transactional
    @Query(value = "UPDATE courses SET course_name=:courseName, course_description=:courseDescription, " +
            "category=:category, course_price=:coursePrice WHERE course_id=:courseId AND author=:author", nativeQuery = true)
    int updateCourse(@Param("courseName") String courseName, @Param("courseDescription") String courseDescription,
                     @Param("category") String category, @Param("coursePrice") Double coursePrice,
                     @Param("courseId") Long courseId, @Param("author") String author);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM courses WHERE course_id=:courseId AND author=:author",nativeQuery = true)
    int deleteCourse(@Param("courseId") Long courseId, @Param("author") String author);

    @Query(value = "SELECT DISTINCT course_id as courseId, category from courses GROUP BY category", nativeQuery = true)
    List<CategoryDTO> getAllCategories();

}
