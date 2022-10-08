package com.techdemy.service;

import com.techdemy.entities.Course;

import java.util.List;

public interface CourseService {

    void addCourse(Course course);

    Course getCourse(Long courseId);

    void updateCourse(Course course);

    void deleteCourse(Long courseId);

    List<Course> getCourses();

}
