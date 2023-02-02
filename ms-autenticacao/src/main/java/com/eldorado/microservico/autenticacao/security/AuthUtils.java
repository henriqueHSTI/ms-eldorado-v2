package com.eldorado.microservico.autenticacao.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class AuthUtils {

    @Value("${eldorado.jwt.secret}")
    private String jwtSecret;
    @Value("${eldorado.jwt.expirationMs}")
    private int jwtTTL;

    public String generationJwtToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var date = Calendar.getInstance().getTime();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + jwtTTL))
                .signWith(SignatureAlgorithm.HS512, getSignSecret())
                .compact();
    }

    @SneakyThrows
    public boolean validateJwtToken(String authToken, jakarta.servlet.http.HttpServletResponse httpServletResponse) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignSecret()).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(getSignSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    private Key getSignSecret() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
