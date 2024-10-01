package com.v1.smartbill.service;

import com.v1.smartbill.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    List<User> findByLastname(String lastName);
    List<User> findByBirthdateGreaterThan(Date birthdate);
    List<User> saveAll(List<User> users);
}
