package com.techdemy.controller;

import com.techdemy.dto.request.ForgetPasswordRequest;
import com.techdemy.dto.request.LoginRequest;
import com.techdemy.dto.request.ResetPasswordRequest;
import com.techdemy.dto.request.SignupRequest;
import com.techdemy.dto.response.AuthResponse;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login user", response = AuthResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Login Successful"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "Signup User", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signup Successful"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signUpUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.signupUser(signupRequest),HttpStatus.OK);
    }

    @ApiOperation(value = "Forget Password", response = GenericResponse.class)
    @PostMapping(value = "/forgetPassword")
    public ResponseEntity<GenericResponse> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        authService.forgetPassword(forgetPasswordRequest);
        return ResponseEntity.ok(GenericResponse
                .buildGenericResponse("Token is sent to the recipient, please check your email"));
    }

    @ApiOperation(value = "Reset Password", response = GenericResponse.class)
    @PutMapping(value = "/resetPassword")
    public ResponseEntity<GenericResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Password updated successfully"));
    }

}
