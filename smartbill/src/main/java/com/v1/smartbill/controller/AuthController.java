package com.v1.smartbill.controller;


import com.v1.smartbill.dto.AuthResponse;
import com.v1.smartbill.dto.LoginDto;
import com.v1.smartbill.dto.RegisterDto;
import com.v1.smartbill.dto.RegisterResponse;
import com.v1.smartbill.model.Role;
import com.v1.smartbill.model.User;
import com.v1.smartbill.repository.IRoleRepository;
import com.v1.smartbill.repository.IUserRepository;
import com.v1.smartbill.security.JwtTokenProvider;
import com.v1.smartbill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/auth/")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRoleRepository roleRepository;
    private IUserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    @Autowired

    public AuthController(JwtTokenProvider jwtTokenProvider, UserService userService, AuthenticationManager authenticationManager, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @PostMapping("register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterDto request) {
        try {
            if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
                return new ResponseEntity<>(new RegisterResponse("El usuario o correo ya existen"), HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setPhone(request.getPhone());
            user.setBirthdate(request.getBirthdate());

            Role roleUser = roleRepository.findByName("USER").orElseThrow(() -> new Exception("Rol no encontrado"));
            user.setRoles(Collections.singletonList(roleUser));

            userRepository.save(user);
            return new ResponseEntity<>(new RegisterResponse("El registro fue exitoso"), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new RegisterResponse("Error de integridad de datos: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new RegisterResponse("Error en el registro: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("register/admin")
    public ResponseEntity<RegisterResponse> registerAdmin(@RequestBody RegisterDto request) {
        try {
            // Verifica si el usuario ya existe por nombre de usuario
            Optional<User> existingUserOpt = userRepository.findByUsername(request.getUsername());

            User user;
            if (existingUserOpt.isPresent()) {
                // Si el usuario existe, se actualizan sus roles
                user = existingUserOpt.get();
                Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new Exception("Rol no encontrado"));

                // Agrega el rol de ADMIN si no lo tiene ya
                if (!user.getRoles().contains(adminRole)) {
                    user.getRoles().add(adminRole);
                }

                userRepository.save(user);
                return new ResponseEntity<>(new RegisterResponse("Roles actualizados para el usuario existente"), HttpStatus.OK);
            } else {
                // Si el usuario no existe, se crea uno nuevo
                user = new User();
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setEmail(request.getEmail());
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setPhone(request.getPhone());

                Role role = roleRepository.findByName("ADMIN").orElseThrow(() -> new Exception("Rol no encontrado"));
                user.setRoles(Collections.singletonList(role));

                userRepository.save(user);
                return new ResponseEntity<>(new RegisterResponse("El registro fue exitoso"), HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new RegisterResponse("Error de integridad de datos: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new RegisterResponse("Error en el registro: Faltan datos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        try {
            // 1. Session authenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. Validar token
            String token = jwtTokenProvider.generateToken(authentication);
            Optional<User> userOpt = userService.findByUsername(request.getUsername());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                AuthResponse authResponse = new AuthResponse(token, user.getUsername(), user.getRoles());
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
