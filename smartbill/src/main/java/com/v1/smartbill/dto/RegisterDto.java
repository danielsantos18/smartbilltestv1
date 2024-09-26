package com.v1.smartbill.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
