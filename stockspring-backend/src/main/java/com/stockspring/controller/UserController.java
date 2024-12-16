package com.stockspring.controller;


import com.stockspring.model.User;
import com.stockspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id){
        User userObj = userService.getUserById(id);
        if (userObj != null) {
        userObj.setEmail(user.getEmail());
        userObj.setUsername(user.getUsername());
        userObj.setPassword(user.getPassword());
        }
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){

        User userObj = userService.getUserById(id);
        String deleteMsg = null;
        if (userObj != null){
            deleteMsg = userService.deleteUser(userObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }
}
