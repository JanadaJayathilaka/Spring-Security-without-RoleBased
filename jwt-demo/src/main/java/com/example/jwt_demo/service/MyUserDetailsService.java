package com.example.jwt_demo.service;

import com.example.jwt_demo.config.SecurityConfig;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public MyUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = User.builder()
                .username("janada")
                .password(passwordEncoder.encode("janada"))
                .build();
        return user;
    }


}
