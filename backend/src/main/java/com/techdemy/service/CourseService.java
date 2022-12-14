package com.techdemy.service;

import com.techdemy.dto.request.CourseRequestDto;
import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.dto.response.CourseResponseDto;

import java.util.List;

public interface CourseService {

    CourseResponseDto saveCourse(CourseRequestDto courseRequestDto);

    CourseResponseDto getCourse(Long courseId);

    void updateCourse(Long courseId, CourseRequestDto courseRequestDto);

    void deleteCourse(Long courseId);

    List<CategoryDTO> getAllCategories();

    List<CourseResponseDto> getCoursesByCategory(String category);

    List<CourseResponseDto> getAllCourses();

}
