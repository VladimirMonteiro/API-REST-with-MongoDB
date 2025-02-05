package com.outercode.ApiMongoDB.services;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();

    }
}
