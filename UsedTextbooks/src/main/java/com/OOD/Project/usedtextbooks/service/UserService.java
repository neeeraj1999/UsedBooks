package com.OOD.Project.usedtextbooks.service;


import com.OOD.Project.usedtextbooks.entity.Book;
import com.OOD.Project.usedtextbooks.entity.User;
import com.OOD.Project.usedtextbooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInter {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllBooks() {
        return userRepository.findAll();
    }
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public Optional<User> updateUserByUsername(String username, User updatedUser) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            User savedUser = userRepository.save(user);
            return Optional.of(savedUser);
        }
        return Optional.empty();
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

