package com.hextech.estoque_api.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.infrastructure.security.utils.CustomUserDetails;
import com.hextech.estoque_api.infrastructure.security.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.audience}")
    private String audience;

    @Value("${security.jwt.expire-length}")
    private long validityInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = getAccessToken(user, now, validity);
        return new TokenDTO(accessToken, now, validity, user);
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

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(tokenContainsBearer(bearerToken)) return bearerToken.substring("Bearer ".length());
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

    private boolean tokenContainsBearer(String bearerToken) {
        return StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ");
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg)
                .withIssuer(issuer)
                .withAudience(audience)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    private String getAccessToken(User user, Date now, Date validity) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withAudience(audience)
                .withIssuedAt(now)
                .withNotBefore(now)
                .withExpiresAt(validity)
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("userId", user.getId())
                .withClaim("roles", user.getRoleNames())
                .withClaim("companyId", user.getCompany().getId())
                .sign(algorithm);
    }
}
