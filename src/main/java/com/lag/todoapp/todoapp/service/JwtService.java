package com.lag.todoapp.todoapp.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String extractEmail(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
