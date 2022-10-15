package com.techdemy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {

    @NotEmpty(message = "To field can't be empty")
    private String to;

    @NotEmpty(message = "Subject field can't be empty")
    private String subject;

    @NotEmpty(message = "Message field can't be empty")
    private String message;

}
