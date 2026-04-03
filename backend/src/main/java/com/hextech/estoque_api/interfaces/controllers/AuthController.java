package com.hextech.estoque_api.interfaces.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.hextech.estoque_api.application.services.AuthService;
import com.hextech.estoque_api.interfaces.controllers.docs.AuthControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<StandardResponse<?>> login(@RequestBody @Valid AccountCredentialsDTO credentials,
                                                     HttpServletResponse response) {
        var token = service.login(credentials);

        setCookies(response, token.getAccessToken(), token.getRefreshToken());

        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<StandardResponse<?>> refreshToken(@RequestBody JsonNode body,
                                                            @RequestHeader("Authorization") String refreshToken,
                                                            HttpServletResponse response) {
        String username = body.get("username").asText();

        if (!parametersAreValid(username, refreshToken))
            throw new IllegalArgumentException("Parâmetros do cliente inválidos.");

        var token = service.refreshToken(username, refreshToken);
        setCookies(response, token.getAccessToken(), token.getRefreshToken());
        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }

    private boolean parametersAreValid(String username, String refreshToken) {
        return StringUtils.isNotBlank(username) || StringUtils.isNotBlank(refreshToken);
    }

    private void setCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        String accessCookie = String.format("accessToken=%s; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=%d",
                accessToken, 60*15);
        String refreshCookie = String.format("refreshToken=%s; HttpOnly; Secure; SameSite=None; Path=/auth/refresh; Max-Age=%d",
                refreshToken, 60*60*24*7);

        response.addHeader("Set-Cookie", accessCookie);
        response.addHeader("Set-Cookie", refreshCookie);
    }
}
