package com.hamsterbusters.squeaker.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private SecretKey secretKey;

    public String getValidToken(int userId, String nickname) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(userId))
                .claim("name", nickname)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * jwtConfig.getTokenExpirationAfterMinutes()))
                .signWith(secretKey)
                .compact();

        return "Bearer " + token;
    }

    public String getExpiredToken() {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(1))
                .claim("name", "Test")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10))
                .signWith(secretKey)
                .compact();

        return "Bearer " + token;
    }

    public String getTokenWithInvalidSignature() {
        String invalidSecret = "InvalidSecretInvalidSecretInvalidSecretInvalidSecretInvalidSecretInvalidSecretInvalidSecretInvalidSecret";
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(1))
                .claim("name", "Test")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(invalidSecret.getBytes()))
                .compact();

        return "Bearer " + token;
    }
    
}
