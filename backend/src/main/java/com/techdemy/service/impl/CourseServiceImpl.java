package com.techdemy.service.impl;

import com.techdemy.entities.Course;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CourseRepository;
import com.techdemy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long courseId) {
        Course course = courseRepository.findById( courseId ).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return course;
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById( courseId );
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
