package com.v1.smartbill.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.v1.smartbill.dto.LoginDto;
import com.v1.smartbill.model.User;
import com.v1.smartbill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/auth/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("findByLastname")
    public ResponseEntity<List<User>> findByLastname(@RequestParam("lastName") String lastName) {
        List<User> userList = userService.findByLastname(lastName);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @GetMapping("findByDate")
    public ResponseEntity<List<User>> findByDate(@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("birthdate")Date birthdate) {
        List<User> userList = userService.findByBirthdateGreaterThan(birthdate);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping("saveAll")
    public ResponseEntity<List<User>> saveAll(@RequestBody List<User> userList) {
        List<User> userList1 = userService.saveAll(userList);
        return new ResponseEntity<List<User>>(userList1,HttpStatus.OK);
    }
}
