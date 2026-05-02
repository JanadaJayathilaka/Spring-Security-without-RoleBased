package com.example.jwt_demo.controller;

import com.example.jwt_demo.dto.LoginRequestDto;
import com.example.jwt_demo.dto.LoginResponseDto;
import com.example.jwt_demo.dto.RegisterRequestDto;
import com.example.jwt_demo.dto.RegisterResponseDto;
import com.example.jwt_demo.entity.UserEntity;
import com.example.jwt_demo.service.Authservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final Authservice authservice;

    public AuthController(Authservice authservice) {
        this.authservice = authservice;
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return authservice.getAllUsers();
    }

    @PostMapping
    public UserEntity createUser(@RequestBody RegisterRequestDto user){
        return authservice.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginData){
        LoginResponseDto res = authservice.login(loginData);
        if(res.getError() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerData){
        RegisterResponseDto res = authservice.register(registerData);
        if(res.getError() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
