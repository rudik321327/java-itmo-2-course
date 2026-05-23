package com.app_with_database.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    // 1) Жёстко заданная константа вместо плейсхолдеров
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(
            // минимум 256 бит (32 байта) для HS256
            "MySuperSecretKeyThatIsAtLeast32BytesLong!".getBytes()
    );

    // 2) Время жизни токена (мс)
    private static final long VALIDITY_IN_MS = 60 * 60 * 1000L; // 1 час

    /**
     * Генерация jwt-токена по username.
     */
    public String generateToken(String username) {
        Date now   = new Date();
        Date exp   = new Date(now.getTime() + VALIDITY_IN_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Валидация токена (подпись + срок действия).
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Извлечение username из токена.
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
