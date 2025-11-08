package com.example.MatchUp.MatchUp_Event.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SecretKey;
    private final long expirationMs = 1000 * 60 * 60; // hour

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        SecretKey = secret;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
