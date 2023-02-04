package com.eldorado.microservico.usuario.mocks;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

public class UtilsMock {

    public static String generationJwtToken() {
        var date = Calendar.getInstance().getTime();
        return Jwts.builder().setSubject("User Mock").setIssuedAt(date).setExpiration(new Date(date.getTime() + 10000000)).signWith(SignatureAlgorithm.HS512, getSignSecret()).compact();
    }

    private static Key getSignSecret() {
        var jwtSecret = "KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D";
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
