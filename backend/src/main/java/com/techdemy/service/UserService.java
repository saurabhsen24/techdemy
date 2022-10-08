package com.techdemy.service;


import com.techdemy.entities.User;

public interface UserService {

    void updateUser( User user );

    User getUser( Long userId );

    void deleteUser( Long userId );

}
