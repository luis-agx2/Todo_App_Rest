package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "AkB63SLoUIV/Mt3mh0T6VC++XZFzAYCdinNdCo3rRu0h5/NY1RS2BYjKmPcHSizV";

    @Override
    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("roles", userDetails.getAuthorities());
        extraClaims.put("email", userDetails.getUsername());
        extraClaims.put("id", userDetails.getId());

        return generateToken(extraClaims, userDetails);
    }

    @Override
    public String extractEmail(String token) {
        return extractClamis(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractClamis(token, Claims::getSubject);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24 * 24 * 7)))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClamis(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClamis(token, Claims::getExpiration).before(new Date());
    }

    private SecretKey getSignInKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
    }
}
