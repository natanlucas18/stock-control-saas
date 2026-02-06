package com.hextech.estoque_api.interfaces.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.hextech.estoque_api.application.services.AuthService;
import com.hextech.estoque_api.interfaces.controllers.docs.AuthControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<?>> login(@RequestBody @Valid AccountCredentialsDTO credentials) {
        var token = service.login(credentials);
        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<StandardResponse<?>> refreshToken(@RequestBody JsonNode body,
                                                            @RequestHeader("Authorization") String refreshToken) {
        String username = body.get("username").asText();

        if (!parametersAreValid(username, refreshToken))
            throw new IllegalArgumentException("Parâmetros do cliente inválidos.");

        var token = service.refreshToken(username, refreshToken);
        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }

    private boolean parametersAreValid(String username, String refreshToken) {
        return StringUtils.isNotBlank(username) || StringUtils.isNotBlank(refreshToken);
    }
}
