package com.techdemy.service.impl;

import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.entities.Enrollment;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.EnrollmentRepository;
import com.techdemy.security.JwtHelper;
import com.techdemy.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<CourseResponseDto> getAllEnrolledCourses() {
        log.info("Fetches all enrollments");

        Long userId = Long.parseLong(JwtHelper.getCurrentLoggedInUserId());
        List<CourseResponseDto> enrolledCourses = enrollmentRepository.getAllEnrolledCourses(userId).stream()
                .map(enrolledCourse -> CourseResponseDto.from(enrolledCourse)).collect(Collectors.toList());

        return enrolledCourses;
    }

}
