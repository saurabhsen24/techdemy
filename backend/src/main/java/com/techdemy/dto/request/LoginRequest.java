package com.techdemy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Username can't be empty")
    private String userName;

    @NotEmpty(message = "password can't be empty")
    private String password;

}
