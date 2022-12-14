package com.techdemy.service;


import com.techdemy.entities.User;

public interface UserService {

    void saveUser(User user);
    void updateUser(User user );
    User getUserByUserName( String userName );
    User getUserByEmail( String email );

    Boolean checkIfUserExistsByUsernameOrEmail(String userName, String email);

    void deleteUser(Long userId );

}
