package com.hextech.estoque_api.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.infrastructure.security.exceptions.InvalidJwtAuthenticationException;
import com.hextech.estoque_api.infrastructure.security.utils.CustomUserDetails;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.audience}")
    private String audience;

    @Value("${security.jwt.access-expire-length}")
    private long accessTokenValidity;

    @Value("${security.jwt.refresh-expire-length}")
    private long refreshTokenValidity;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);
        String accessToken = getAccessToken(user, now, validity);
        String refreshToken = getRefreshToken(user, now);
        return new TokenDTO(accessToken, refreshToken, accessTokenValidity, user);
    }

    public TokenDTO refreshToken(User user, String refreshToken) {
        if (tokenContainsBearer(refreshToken))
            refreshToken = refreshToken.substring("Bearer ".length());

        DecodedJWT decodedJWT = decodedToken(refreshToken);
        String username = decodedJWT.getSubject();

        if (!username.equals(user.getUsername()))
            throw new InvalidJwtAuthenticationException("Usuário inválido!");

        return createAccessToken(user);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        Long companyId = decodedJWT.getClaim("companyId").asLong();
        Long userId = decodedJWT.getClaim("userId").asLong();
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .toList();

        UserDetails userDetails = new CustomUserDetails(userId, companyId, username, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && tokenContainsBearer(authHeader)) return authHeader.substring("Bearer ".length());

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && tokenContainsBearer(authHeader)) return authHeader.substring("Bearer ".length());

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            decodedToken(token);
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Token inválido ou expirado", e);
        }
    }

    public long extractRemainingTime(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        Date expiration = decodedJWT.getExpiresAt();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        return decodedJWT.getSubject();
    }

    private boolean tokenContainsBearer(String bearerToken) {
        return StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ");
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg)
                .withIssuer(issuer)
                .withAudience(audience)
                .build();
        return verifier.verify(token);
    }

    private String getAccessToken(User user, Date now, Date validity) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withAudience(audience)
                .withIssuedAt(now)
                .withNotBefore(now)
                .withExpiresAt(validity)
                .withClaim("userId", user.getId())
                .withClaim("roles", user.getRoleNames())
                .withClaim("companyId", user.getCompany().getId())
                .sign(algorithm);
    }

    private String getRefreshToken(User user, Date now) {
        return getAccessToken(user, now, new Date(now.getTime() + refreshTokenValidity));
    }
}
