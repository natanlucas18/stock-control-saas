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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService service;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${security.jwt.access-expire-length}")
    private long accessTokenValidity;

    @Value("${security.jwt.refresh-expire-length}")
    private long refreshTokenValidity;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<?>> login(@RequestBody @Valid AccountCredentialsDTO credentials,
                                                     HttpServletResponse response) {
        var token = service.login(credentials);

        setCookies(response, token.getAccessToken(), token.getRefreshToken(),
                token.getUserRoles(), accessTokenValidity, refreshTokenValidity);

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
        setCookies(response, token.getAccessToken(), token.getRefreshToken(),
                token.getUserRoles(), accessTokenValidity, refreshTokenValidity);
        return ResponseEntity.ok().body(new StandardResponse<>(true, token));
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        setCookies(response, "", "", List.of(), 0, 0);
        response.setStatus(204);
    }

    private boolean parametersAreValid(String username, String refreshToken) {
        return StringUtils.isNotBlank(username) || StringUtils.isNotBlank(refreshToken);
    }

    private void setCookies(HttpServletResponse response, String accessToken, String refreshToken,
                            List<String> roles, long accessTokenValidity, long refreshTokenValidity) {
        boolean isProd = profile.equals("prod");

        String sameSite = isProd ? "None" : "Lax";
        String secure = isProd ? "Secure" : "";

        String accessCookie = String.format("accessToken=%s; HttpOnly; %s; SameSite=%s; Path=/; Max-Age=%d;",
                accessToken, secure, sameSite, accessTokenValidity/1000);
        String refreshCookie = String.format("refreshToken=%s; HttpOnly; %s; SameSite=%s; Path=/; Max-Age=%d;",
                refreshToken, secure, sameSite, refreshTokenValidity/1000);
        String userRolesCookie = String.format("userRoles=%s; HttpOnly; %s; SameSite=%s; Path=/; Max-Age=%d;",
                roles, secure, sameSite, accessTokenValidity/1000);

        response.addHeader("Set-Cookie", accessCookie);
        response.addHeader("Set-Cookie", refreshCookie);
        response.addHeader("Set-Cookie", userRolesCookie);
    }
}
