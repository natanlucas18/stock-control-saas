package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.application.usecases.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountCredentialsDTO credentials) {
        var token = service.login(credentials);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        return ResponseEntity.ok().body(token);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
//
//        System.out.println("Recebi login: " + credentials.getUsername());
//
//        if (credentialsIsInvalid(credentials))return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
//        var token = service.signIn(credentials);
//
//        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
//        return  ResponseEntity.ok().body(token);
//    }
//
//    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
//        return credentials == null ||
//                StringUtils.isBlank(credentials.getPassword()) ||
//                StringUtils.isBlank(credentials.getUsername());
//    }
}
