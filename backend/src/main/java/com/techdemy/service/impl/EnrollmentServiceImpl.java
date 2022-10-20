package com.techdemy.service.impl;

import com.techdemy.dto.request.EnrollRequest;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.entities.Course;
import com.techdemy.entities.Enrollment;
import com.techdemy.entities.User;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CartRepository;
import com.techdemy.repository.CourseRepository;
import com.techdemy.repository.EnrollmentRepository;
import com.techdemy.repository.UserRepository;
import com.techdemy.service.EnrollmentService;
import com.techdemy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<CourseResponseDto> getAllEnrolledCourses() {
        log.info("Fetches all enrollments");

        Long userId = Utils.getCurrentLoggedInUserId();
        List<CourseResponseDto> enrolledCourses = enrollmentRepository.getAllEnrolledCourses(userId).stream()
                .map(enrolledCourse -> CourseResponseDto.from(enrolledCourse)).collect(Collectors.toList());

        return enrolledCourses;
    }

    @Override
    @Transactional
    public void addCourses(EnrollRequest enrollRequest) {
        Long userId = Utils.getCurrentLoggedInUserId();

        log.debug("Adds enrolled courses for user {}", userId);

        List<Long> courseIds = enrollRequest.getCourseIds();
        cartRepository.deleteAllByUserIdAndCourses(userId, courseIds);
        List<Course> courses = courseRepository.findAllById(courseIds);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Enrollment> enrollments = courses.stream().map(course -> Enrollment.from(user,course))
                .collect(Collectors.toList());

        enrollmentRepository.saveAll(enrollments);
    }

}
