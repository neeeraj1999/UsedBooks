package com.OOD.Project.usedtextbooks.service;

import com.OOD.Project.usedtextbooks.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInter {
    List<User> getAllBooks();

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    Optional<User> updateUserByUsername(String username, User updatedUser);

    List<User> getAllUsers();
}
