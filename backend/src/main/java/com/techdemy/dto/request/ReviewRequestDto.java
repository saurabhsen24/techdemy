package com.techdemy.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ReviewRequestDto {

    @NotBlank(message = "Review comment can't be blank")
    private String comment;

    @PositiveOrZero
    private Integer rating;

}
