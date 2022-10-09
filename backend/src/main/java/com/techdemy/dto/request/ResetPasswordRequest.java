package com.techdemy.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ResetPasswordRequest {

    @NotEmpty(message = "Token can't be empty")
    private String token;

    @NotEmpty(message = "Email can't be empty")
    private String email;

    @NotEmpty(message = "New Password can't be empty")
    private String newPassword;
    
}
