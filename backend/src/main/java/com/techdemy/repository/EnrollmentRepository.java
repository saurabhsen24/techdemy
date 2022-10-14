package com.techdemy.repository;

import com.techdemy.entities.Course;
import com.techdemy.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query(value = "SELECT * FROM enrollments e INNER JOIN courses c ON e.course_id=c.course_id " +
            "WHERE e.user_id=:userId",nativeQuery = true)
    List<Course> getAllEnrolledCourses(@Param("userId") Long userId);

}
