package com.techdemy.service;

import com.techdemy.dto.request.ForgetPasswordRequest;
import com.techdemy.dto.request.LoginRequest;
import com.techdemy.dto.request.ResetPasswordRequest;
import com.techdemy.dto.request.SignupRequest;
import com.techdemy.dto.response.AuthResponse;
import com.techdemy.dto.response.GenericResponse;

public interface AuthService {
    AuthResponse loginUser(LoginRequest loginRequest);
    GenericResponse signupUser(SignupRequest signupRequest);
    void resetPassword( ResetPasswordRequest resetPasswordRequest );
    void forgetPassword(ForgetPasswordRequest forgetPasswordRequest);

}
