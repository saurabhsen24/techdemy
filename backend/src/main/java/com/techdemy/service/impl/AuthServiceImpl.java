package com.techdemy.service.impl;

import com.techdemy.dto.request.ForgetPasswordRequest;
import com.techdemy.dto.request.LoginRequest;
import com.techdemy.dto.request.ResetPasswordRequest;
import com.techdemy.dto.request.SignupRequest;
import com.techdemy.dto.response.AuthResponse;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.entities.User;
import com.techdemy.enums.Role;
import com.techdemy.exception.BadRequestException;
import com.techdemy.security.JwtHelper;
import com.techdemy.service.AuthService;
import com.techdemy.service.ResetTokenService;
import com.techdemy.service.UserService;
import com.techdemy.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResetTokenService resetTokenService;

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {

        log.info("Login request initiated for user {}", loginRequest.getUserName());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String,String> claims = new HashMap<>();
        User user = userService.getUserByUserName(loginRequest.getUserName());

        claims.put(Constants.CLAIMS_USERID, user.getUserId().toString());
        claims.put(Constants.CLAIMS_USERNAME, loginRequest.getUserName());
        claims.put(Constants.AUTHORITIES_CLAIM_NAME, user.getRole().name());

        String jwtToken = jwtHelper.createJwtForClaims(Constants.CLAIMS_USERNAME, claims);

        return AuthResponse.builder()
                .userName(loginRequest.getUserName())
                .accessToken(jwtToken)
                .role(user.getRole().name())
                .expiresAt(jwtHelper.getTokenExpirationDate().toString())
                .build();
    }

    @Override
    public GenericResponse signupUser(SignupRequest signupRequest) {
        log.info("Signup request initiated for user {}", signupRequest.getUsername());

        if(userService.checkIfUserExistsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())) {
            throw new BadRequestException("Username/Email already exists");
        }

        User user = User.builder()
                .userName(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .role(Role.USER)
                .build();

        userService.saveUser(user);

        log.info("Signup successful for user {}", signupRequest.getUsername());

        return GenericResponse.builder()
                .message("User successfully signed up")
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        log.debug("Resets password for user {}", resetPasswordRequest.getEmail());
        resetTokenService.validateResetToken(resetPasswordRequest.getToken());

        User user = userService.getUserByEmail(resetPasswordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userService.saveUser(user);

        log.debug("User's {} password updated", user.getUserName());
        resetTokenService.deleteToken(resetPasswordRequest.getToken());

    }

    @Override
    public void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        log.debug("Forget password for user {}", forgetPasswordRequest.getEmail());
        User user = userService.getUserByEmail(forgetPasswordRequest.getEmail());
        resetTokenService.generateToken(user);
    }
}
