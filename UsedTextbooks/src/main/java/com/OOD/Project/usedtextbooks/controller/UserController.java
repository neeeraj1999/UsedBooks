package com.OOD.Project.usedtextbooks.controller;

import com.OOD.Project.usedtextbooks.entity.User;
import com.OOD.Project.usedtextbooks.repository.UserRepository;
import com.OOD.Project.usedtextbooks.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //end point to search user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        System.out.println("Searching for username: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found in the database");
            return ResponseEntity.notFound().build();
        }
        System.out.println("User found: " + user.toString());
        return ResponseEntity.ok(user);
    }
    // Endpoint to create a new user (typically for registration)
    @PostMapping("/admin/add")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Endpoint to update a user's information
    @PutMapping("/admin/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        Optional<User> userOptional = userService.updateUserByUsername(username, updatedUser);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + username + " not found");
        }
    }


    @DeleteMapping("/admin/delete/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found in the database");
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        System.out.println("User deleted successfully");
        return ResponseEntity.ok().build();
    }


}
