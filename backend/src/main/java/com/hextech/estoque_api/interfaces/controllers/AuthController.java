package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.AuthService;
import com.hextech.estoque_api.interfaces.controllers.docs.AuthControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<StandardResponse<?>> login(@RequestBody AccountCredentialsDTO credentials) {
        var token = service.login(credentials);
        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }
}
