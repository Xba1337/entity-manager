package ru.spring.entity_manager.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenManager {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtTokenManager(@Value("${jwt.secret-key}") String secretKey,
                           @Value("${jwt.lifetime}") long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationTime = expirationTime;
    }

    public String generateToken(String login) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(login)
                .signWith(secretKey)
                .issuedAt(now)
                .expiration(expiration)
                .compact();
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
