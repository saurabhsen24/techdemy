package com.techdemy.dto.response;

import com.techdemy.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String category;
    private String author;
    private Double courseRating;
    private Double coursePrice;
    private String courseImage;

    public static CourseResponseDto from(Course course) {
        CourseResponseDto courseResponseDto = CourseResponseDto.builder()
                .courseId(course.getCourseId())
                .author(course.getAuthor())
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .category(course.getCategory())
                .coursePrice(course.getCoursePrice())
                .courseRating(course.getCourseRating())
                .courseImage(course.getCourseImage())
                .build();

        return courseResponseDto;
    }
}
