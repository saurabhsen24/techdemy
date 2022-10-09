package com.techdemy.controller;

import com.techdemy.dto.request.CourseRequestDto;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "Saves course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course saved in db successfully"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @PostMapping(value = "/saveCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> saveCourse(@Valid @RequestBody CourseRequestDto courseRequestDto){
        log.info("Received request to save course {}", courseRequestDto.getCourseName());
        courseService.saveCourse(courseRequestDto);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course created successfully"));
    }

    @ApiOperation(value = "Fetches course from db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches course successfully", response = CourseResponseDto.class),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @GetMapping(value = "/{courseId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable("courseId") Long courseId) {
        log.info("Got request to fetch course {}", courseId);
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @ApiOperation(value = "Updates course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updates course successfully"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @PutMapping(value = "/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> updateCourse(@RequestBody CourseRequestDto courseRequestDto,
                                             @PathVariable("courseId") Long courseId) {
        log.info("Got request to update course, {}", courseId);
        courseService.updateCourse(courseRequestDto);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course updated successfully"));
    }

    @ApiOperation(value = "Deletes course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updates course successfully"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @DeleteMapping(value = "/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> deleteCourse(@PathVariable("courseId") Long courseId) {
        log.info("Got request to delete course, {}", courseId);
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course deleted successfully"));
    }

    @ApiOperation(value = "Fetches courses by category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all courses"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @GetMapping(value = "/{category}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByCategory(@PathVariable("category") String category) {
        log.info("Got request to fetch courses by category, {}", category);
        return ResponseEntity.ok(courseService.getCoursesByCategory(category.trim()));
    }

    @ApiOperation(value = "Fetches all courses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all courses"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        log.info("Got request to fetch all courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

}
