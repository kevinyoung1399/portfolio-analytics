package com.example.portfolioanalytics.controller;

import com.example.portfolioanalytics.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @PostMapping("/api/register")
    public String registerUser(@RequestBody User user) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setDisplayName(user.getName());

        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return "Successfully created user: " + userRecord.getUid();
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
    }
    @GetMapping("/hello/world")
    public String helloWorld() {
        return "hello world";
    };

}
