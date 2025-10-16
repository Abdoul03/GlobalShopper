package com.globalshopper.GlobalShopper.config;

import com.globalshopper.GlobalShopper.entity.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.security.PublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecret;
    private SecretKey secretKey;

    //private final Key jwtSecretkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    //private String secretKey;

    private final long accessTokenValidity = 15L * 60L * 1000L ;
    @Getter
    private final long refreshTokenValidity = 30L * 24L * 60L * 60L * 1000L;

    @PostConstruct
    void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret.getBytes(StandardCharsets.UTF_8)));
    }

    private String generateToken(Long userId, TokenType tokenType, long expireAt, Role userRole){
        Date now = Date.from(Instant.now());
        Date expiration = new Date(now.getTime() + expireAt);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", tokenType.name())
                .claim("role", userRole)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

    }

    public String generateAccessToken( Long userId, Role userRoles){
        return generateToken(userId, TokenType.ACCESS_TOKEN, accessTokenValidity, userRoles);
    }
    public String generateRefreshToken(Long userId, Role userRoles){
        return generateToken(userId, TokenType.REFRESH_TOKEN, refreshTokenValidity, userRoles);
    }

    public boolean isTokenAccessValid(String accessToken) {
        Claims claims = parseAllClaims(accessToken);
        if (claims == null) return false;
        String type = (String) claims.get("type");
        return TokenType.ACCESS_TOKEN.name().equals(type);
    }
    public boolean isTokenRefreshValid(String refreshToken){
        Claims claims = parseAllClaims(refreshToken);
        System.out.println("Avant claims");
        if (claims == null) return false;
        String type = (String) claims.get("type");
        System.out.println(type);
        return TokenType.REFRESH_TOKEN.name().equals(type);
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = parseAllClaims(token);
        if (claims == null) throw new IllegalArgumentException("Token invalide.");
        return Long.parseLong(claims.getSubject());
    }
    public Role getUserRolesFromToken(String token) {
        Claims claims = parseAllClaims(token);
        if (claims == null) throw new IllegalArgumentException("Token invalide");
        Object roleClaim = claims.get("role");
        if (roleClaim == null) {
            throw new IllegalArgumentException("Aucun rôle trouvé dans le token");
        }
        return Role.valueOf(roleClaim.toString());
    }


    private Claims parseAllClaims(String token) {
        String rawToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .getPayload();
    }


    private enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }
}
