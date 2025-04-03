package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(CustomUserDetails userDetails);

    String extractEmail(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
