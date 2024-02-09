package com.OOD.Project.usedtextbooks.repository;



import com.OOD.Project.usedtextbooks.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // To retrieve a user by their username.
    Optional<User> findByUsernameAndPassword(String username, String password);
}

