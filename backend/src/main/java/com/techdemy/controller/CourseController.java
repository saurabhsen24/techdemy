package com.techdemy.controller;

import com.techdemy.dto.request.CourseRequestDto;
import com.techdemy.dto.response.CategoryDTO;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(value = "/saveCourse", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponseDto> saveCourse(@Valid @RequestPart("courseRequest") CourseRequestDto
                                                                  courseRequestDto, @RequestPart MultipartFile file){
        log.info("Received request to save course {}", courseRequestDto.getCourseName());
        courseRequestDto.setFile( file );
        return ResponseEntity.ok( courseService.saveCourse(courseRequestDto) );
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
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @PutMapping(value = "/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> updateCourse(@Valid @RequestBody CourseRequestDto courseRequestDto,
                                             @PathVariable("courseId") Long courseId) {
        log.info("Got request to update course, {}", courseId);
        courseService.updateCourse(courseId ,courseRequestDto);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course updated successfully"));
    }

    @ApiOperation(value = "Deletes course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updates course successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You can not access this resource")
    })
    @DeleteMapping(value = "/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> deleteCourse(@PathVariable("courseId") Long courseId) {
        log.info("Got request to delete course, {}", courseId);
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course deleted successfully"));
    }

    @ApiOperation(value = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "Fetches all categories" ),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        log.info("Fetches all categories");
        return ResponseEntity.ok(courseService.getAllCategories());
    }

    @ApiOperation(value = "Fetches courses by category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all courses"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping(value = "/categories/{category}")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByCategory(@PathVariable("category") String category) {
        log.info("Got request to fetch courses by category, {}", category);
        return ResponseEntity.ok(courseService.getCoursesByCategory(category.trim()));
    }

    @ApiOperation(value = "Fetches all courses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all courses"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        log.info("Got request to fetch all courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

}
