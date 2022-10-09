package com.techdemy.service;

import com.techdemy.entities.User;

public interface ResetTokenService {

    void generateToken(User user);

    void validateResetToken(String token);
}
