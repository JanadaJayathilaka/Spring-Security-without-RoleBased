package com.example.jwt_demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String healthCheck(){
        System.out.println("Health check endpoint hit");
        return "Hello, World!";
    }

    @PostMapping("/login")
    public String login(){
        return "Login successful!";
    }
}
