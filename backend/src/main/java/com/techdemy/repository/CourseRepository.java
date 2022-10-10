package com.techdemy.repository;

import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByCategory( String category );

    @Query(value = "SELECT DISTINCT course_id as courseId, category from courses GROUP BY category", nativeQuery = true)
    List<CategoryDTO> getAllCategories();

}
