package com.techdemy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "Course Name can't be blank")
    private String courseName;

    @NotBlank(message = "Course category can't be blank")
    private String category;

    @NotBlank(message = "Course description can't be blank")
    private String courseDescription;

    @NotBlank(message = "Course price can't be blank")
    private Double coursePrice;

}
