package com.raj.service;

import com.raj.model.User;
import com.raj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long uid) {
        return userRepository.findById(uid).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
