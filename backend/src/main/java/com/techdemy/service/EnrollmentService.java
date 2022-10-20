package com.techdemy.service;

import com.techdemy.dto.request.EnrollRequest;
import com.techdemy.dto.response.CourseResponseDto;

import java.util.List;

public interface EnrollmentService {

    void addCourses(EnrollRequest enrollRequest);

    List<CourseResponseDto> getAllEnrolledCourses();

}
