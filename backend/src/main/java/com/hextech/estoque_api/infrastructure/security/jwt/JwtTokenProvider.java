package com.hextech.estoque_api.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hextech.estoque_api.application.dtos.security.TokenDTO;
import com.hextech.estoque_api.domain.entities.User;
import com.hextech.estoque_api.infrastructure.security.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${spring.security.jwt.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${spring.security.jwt.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(User user, List<String> roles, Long clientId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = "Bearer " + getAccessToken(user, roles, now, validity, clientId);
        return new TokenDTO(user.getUsername(), now, validity, accessToken);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(tokenContainsBearer(bearerToken)) return bearerToken.substring("Bearer ".length());
        return null;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            if(decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Token inválido ou expirado");
        }
    }

    private boolean tokenContainsBearer(String bearerToken) {
        return StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ");
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    private String getAccessToken(User user, List<String> roles, Date now, Date validity, Long clientId) {
        String issuerURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withClaim("clientId", clientId)
                .withClaim("userId", user.getId())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(user.getUsername())
                .withIssuer(issuerURL)
                .sign(algorithm);
    }
}
