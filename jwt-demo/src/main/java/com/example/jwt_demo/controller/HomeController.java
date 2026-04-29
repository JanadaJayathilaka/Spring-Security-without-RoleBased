package com.example.jwt_demo.controller;


import com.example.jwt_demo.service.JWTService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    private final JWTService jwtService;

    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public String healthCheck(){
        System.out.println("Health check endpoint hit");
        return "Hello, World!";
    }

    @PostMapping("/login")
    public String login(){
        return jwtService.getJwtToken();
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token){
        return jwtService.getUsername(token);
    }
}
