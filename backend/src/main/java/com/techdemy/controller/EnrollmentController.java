package com.techdemy.controller;

import com.techdemy.dto.request.EnrollRequest;
import com.techdemy.dto.response.CourseResponseDto;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.EnrollmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/enrolled")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @ApiOperation(value = "Adds enrolled courses")
    @ApiResponses(value = {
            @ApiResponse( code = 200, message = "200" ),
            @ApiResponse( code = 401, message = "You are not authenticated" )
    })
    @PostMapping(value = "/courses")
    public ResponseEntity<Void> addCourses(@RequestBody EnrollRequest enrollRequest) {
        enrollmentService.addCourses(enrollRequest);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Gets all enrolled courses", response = CourseResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all enrolled courses"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/courses")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        log.info("Got request to fetch all enrolled courses");
        return ResponseEntity.ok(enrollmentService.getAllEnrolledCourses());
    }

}
