package com.v1.smartbill.service;

import com.v1.smartbill.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findByLastname(String lastName);
}
