package com.techdemy.service.impl;

import com.techdemy.dto.request.CourseRequestDto;
import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.entities.Course;
import com.techdemy.exception.BadRequestException;
import com.techdemy.exception.ForbiddenResourceException;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CourseRepository;
import com.techdemy.security.JwtHelper;
import com.techdemy.service.CourseService;
import com.techdemy.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public CourseResponseDto saveCourse(CourseRequestDto courseRequestDto) {
        log.debug("Get request to save course {}", courseRequestDto.getCourseName());

        String courseImageUrl = null;
        try {
            courseImageUrl = fileUploadService.uploadFile(courseRequestDto.getFile());
        } catch (IOException e) {
            log.warn("Course Image didn't upload {}", ExceptionUtils.getStackTrace(e));
            throw new BadRequestException(e.getMessage());
        }

        Course course = Course.from(courseRequestDto);
        course.setCourseImage(courseImageUrl);
        courseRepository.save(course);
        return CourseResponseDto.from(course);
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

        String currentLoggedInUser = JwtHelper.getCurrentLoggedInUsername();

        int numberOfUpdatedRecord = courseRepository.updateCourse(courseRequestDto.getCourseName(),
                courseRequestDto.getCourseDescription(), courseRequestDto.getCategory(),
                courseRequestDto.getCoursePrice(), courseId, currentLoggedInUser );

        if( numberOfUpdatedRecord == 0 ) {
            throw new ForbiddenResourceException("You are forbidden to update course");
        }

    }

    @Override
    public void deleteCourse(Long courseId) {
        log.info("Deleting course with courseId {}", courseId);

        String currentLoggedInUser = JwtHelper.getCurrentLoggedInUsername();

        int numberOfDeletedRecords = courseRepository.deleteCourse(courseId, currentLoggedInUser);

        if( numberOfDeletedRecords == 0 ) {
            throw new ForbiddenResourceException("You are forbidden to delete course");
        }

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
