package com.eldorado.commons.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
@Slf4j
public class AuthUtils {

    @Value("${eldorado.jwt.secret}")
    private String jwtSecret;

    public static final List<String> AUTHORIZED_PATHS = List.of("/user/create", "/user/login", "/actuator/health");

    @SneakyThrows
    public boolean validateJwtToken(String authToken, HttpServletResponse httpServletResponse) {
        if (!StringUtils.hasText(authToken)) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        } else {
            try {
                Jwts.parserBuilder().setSigningKey(getSignSecret()).build().parseClaimsJws(getJwtToken(authToken));
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
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignSecret()).build().parseClaimsJws(token).toString();
    }

    private Key getSignSecret() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getJwtToken(String token) {
        return token.replace("Bearer ", "");
    }

}
