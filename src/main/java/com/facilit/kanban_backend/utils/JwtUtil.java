package com.facilit.kanban_backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "UeePvtlSj1gedxKjQaTRRocT8U2UuhZVR4LgI9BektatvIbfPNBOBbq2rq9GNDNXfLG2vzq5PMntC1Ryl71UTWwFsU4nNff7SPlr1TWq4RjA0iZCFblpUZ3o5NIo9foj7nHko3lh9HJejd3nXjQSN7KU0nZxSOVQFIxHr5wGFG5uOpUg5jfMirEExEQeg5HLF7naKOg2Jnb2sWMKCtaWAAascjlHTjtDIkvselfLiHW7Rqy0wLrqiFTLA9VY6UFn"; // ideal usar pelo menos 256 bits
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Criar o token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // usuário dono do token
                .setIssuedAt(new Date()) // data de criação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token
    public static void validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);

        if (!extractedUsername.equals(username) || isTokenExpired(token)) {
            throw new JwtException("Invalid or expired token");
        }
    }

    // Extrair usuário do token
    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Verifica se está expirado
    private static boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extrair claims (payload do token)
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}