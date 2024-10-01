package com.v1.smartbill.service.impl;

import com.v1.smartbill.model.Role;
import com.v1.smartbill.model.User;
import com.v1.smartbill.repository.IUserRepository;
import com.v1.smartbill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserRepository userRepository;

    public Collection<GrantedAuthority> mapAuthorities(List<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapAuthorities(user.getRoles()));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public List<User> findByLastname(String lastName) {
        return userRepository.findByLastname(lastName);
    }

    @Override
    public List<User> findByBirthdateGreaterThan(Date birthdate) {
        List<User> user = userRepository.findByBirthdateGreaterThan(birthdate);
        return userRepository.findByBirthdateGreaterThan(birthdate);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        List<User> user = userRepository.saveAll(users);
        return userRepository.saveAll(user);
    }

}
