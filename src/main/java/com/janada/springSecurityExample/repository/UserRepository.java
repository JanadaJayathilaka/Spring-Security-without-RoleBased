package com.janada.springSecurityExample.repository;

import com.janada.springSecurityExample.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{

    Users findByUsername(String username);
}
