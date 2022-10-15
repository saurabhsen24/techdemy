package com.techdemy.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class EnrollRequest {

    @NotBlank
    private List<Long> courseIds;

}
