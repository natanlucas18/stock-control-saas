package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.users.UserRequestDTO;
import com.hextech.estoque_api.application.dtos.users.UserResponseDTO;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AuthContext authContext;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO requestDTO) {

        var userResponseDTO = service.createNewUser(requestDTO, authContext.getCurrentCompanyId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}
