package com.v1.smartbill.dto;

import com.v1.smartbill.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String username;
    private List<Role> roles; // o cualquier otro campo que desees

        // Constructor
        public AuthResponse(String token, String username, List<Role> roles) {
            this.token = token;
            this.username = username;
            this.roles = roles;
        }

    }
