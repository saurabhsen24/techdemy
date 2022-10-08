package com.techdemy.service.impl;

import com.techdemy.entities.User;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.UserRepository;
import com.techdemy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        log.debug("Saving user in db {}", user.getUserName());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        log.debug("Fetch user details for user {}", userName);

        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        return user;

    }

    @Override
    public Boolean checkIfUserExistsByUsernameOrEmail(String userName, String email) {
        return userRepository.existsByUserNameOrEmail(userName, email);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
