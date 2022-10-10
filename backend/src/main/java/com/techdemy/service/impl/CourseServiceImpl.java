package com.techdemy.service.impl;

import com.techdemy.dto.request.CourseRequestDto;
import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.entities.Course;
import com.techdemy.exception.BadRequestException;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CourseRepository;
import com.techdemy.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void saveCourse(CourseRequestDto courseRequestDto) {
        log.debug("Get request to save course {}", courseRequestDto.getCourseName());

        Course course = Course.from(courseRequestDto);
        courseRepository.save(course);
    }

    @Override
    public CourseResponseDto getCourse(Long courseId) {
        log.debug("Fetches course, {}", courseId);
        Course course = courseRepository.findById( courseId ).orElseThrow(() ->
                new ResourceNotFoundException("Course not found"));
        return CourseResponseDto.from(course);
    }


    @Override
    public void updateCourse(Long courseId, CourseRequestDto courseRequestDto) {
        log.debug("Updating the course, {}", courseRequestDto.getCourseName());
        Course course = Course.from(courseRequestDto);
        course.setCourseId(courseId);
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        log.info("Deleting course with courseId {}", courseId);
        courseRepository.deleteById( courseId );
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetches all categories");
        return courseRepository.getAllCategories();
    }

    @Override
    public List<CourseResponseDto> getCoursesByCategory(String category) {
        log.debug("Fetch courses based on category, {}", category);

        if(StringUtils.isBlank(category)) {
            throw new BadRequestException("Category can't be blank");
        }

        List<CourseResponseDto> courses = courseRepository.findByCategory(category).stream()
                .map(course -> CourseResponseDto.from(course)).collect(Collectors.toList());

        return courses;
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {
        List<CourseResponseDto> courses = courseRepository.findAll().stream().map(course -> CourseResponseDto.from(course))
                .collect(Collectors.toList());
        return courses;
    }

}
