package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.application.services.AuthService;
import com.hextech.estoque_api.interfaces.controllers.docs.AuthControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountCredentialsDTO credentials) {
        var token = service.login(credentials);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas!");
        }
        return ResponseEntity.ok().body(token);
    }
}
