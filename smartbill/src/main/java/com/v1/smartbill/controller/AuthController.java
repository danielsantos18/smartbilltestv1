package com.v1.smartbill.controller;


import com.v1.smartbill.dto.AuthResponse;
import com.v1.smartbill.dto.LoginDto;
import com.v1.smartbill.dto.RegisterDto;
import com.v1.smartbill.model.Role;
import com.v1.smartbill.model.User;
import com.v1.smartbill.repository.IRoleRepository;
import com.v1.smartbill.repository.IUserRepository;
import com.v1.smartbill.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRoleRepository roleRepository;
    private IUserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto request){
        if (userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("el usuario no existe", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("Registro existoso", HttpStatus.CREATED);
    }

    @PostMapping("register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDto request){
        if (userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("el usuario no existe", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        Role role = roleRepository.findByName("ADMIN").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("Registro existoso", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        try {

            //1. Session authenticationManager
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //2. Validar token
            String token = jwtTokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
