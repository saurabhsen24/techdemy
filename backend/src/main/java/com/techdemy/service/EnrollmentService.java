package com.techdemy.service;

import com.techdemy.dto.response.CourseResponseDto;

import java.util.List;

public interface EnrollmentService {
    List<CourseResponseDto> getAllEnrolledCourses();

}
