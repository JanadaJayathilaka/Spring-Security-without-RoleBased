package com.janada.springSecurityExample.service;

import com.janada.springSecurityExample.model.UserPrincipal;
import com.janada.springSecurityExample.model.Users;
import com.janada.springSecurityExample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if(user==null){
            System.out.println("user not found");
            throw  new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
