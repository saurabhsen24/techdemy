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

    @Query(value = "SELECT c FROM Course c INNER JOIN Enrollment e ON c.courseId = e.course.courseId " +
            "WHERE e.user.userId=:userId")
    List<Course> getAllEnrolledCourses(@Param("userId") Long userId);

}
