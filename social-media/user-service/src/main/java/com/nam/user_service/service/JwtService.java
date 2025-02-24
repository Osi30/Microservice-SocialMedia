package com.nam.user_service.service;

import com.nam.user_service.entity.User;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
    String getUsernameFromToken(String token);
    User getUserFromToken(String token);
}
