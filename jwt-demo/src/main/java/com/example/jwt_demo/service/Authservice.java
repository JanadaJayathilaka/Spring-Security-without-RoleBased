package com.example.jwt_demo.service;

import com.example.jwt_demo.Filter.JwtFilter;
import com.example.jwt_demo.dto.LoginRequestDto;
import com.example.jwt_demo.dto.LoginResponseDto;
import com.example.jwt_demo.dto.RegisterRequestDto;
import com.example.jwt_demo.dto.RegisterResponseDto;
import com.example.jwt_demo.entity.UserEntity;
import com.example.jwt_demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Authservice {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public Authservice(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity createUser(RegisterRequestDto userData){
        UserEntity newUser = new UserEntity(
                userData.getUsername(),
                passwordEncoder.encode(userData.getPassword()),
                userData.getName(),
                userData.getEmail()
        );
        return userRepository.save(newUser);
    }


    public LoginResponseDto login(LoginRequestDto loginData){
//        Boolean userPresent = isUserEnable(loginData.getUsername());
//        if(!userPresent){
//            return new LoginResponseDto(null,null, "user not found","Error");
//        }
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginData.getUsername(),
                            loginData.getPassword()
                    )
            );
        } catch (Exception e) {
            return new LoginResponseDto(null,null, "Invalid username or password","Error");
        }

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role","User");
        claims.put("email", "company@gmail.com");

        String token = jwtService.getJwtToken(loginData.getUsername(), claims);

        System.out.println(jwtService.getFieldFromToken(token, "role"));

        return new LoginResponseDto(token, LocalDateTime.now(), null,"Success");
    }

    public RegisterResponseDto register(RegisterRequestDto req){
        if(isUserEnable(req.getUsername())){
            return new RegisterResponseDto( null,"Username already taken");
        }
        var userData = this.createUser(req);
        if(userData.getId()==null){
            return new RegisterResponseDto( null,"System error");
        }
        return new RegisterResponseDto(String.format("user registered at %s", userData.getId()), null);

    }

    private Boolean isUserEnable(String username){
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if(userEntity == null){
            return false;
        }
        return true;
    }
}
