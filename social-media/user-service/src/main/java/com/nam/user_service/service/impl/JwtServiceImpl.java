package com.nam.user_service.service.impl;

import com.nam.user_service.config.JwtConstant;
import com.nam.user_service.entity.User;
import com.nam.user_service.repository.UserRepository;
import com.nam.user_service.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes());

    @Override
    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 30)))
                .claim("username", authentication.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return String.join(",", roles);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.substring(7))
                .getBody().get("username", String.class);
    }

    @Override
    public User getUserFromToken(String token) {
        String username = getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Cannot find user with username: " + username);
        }
        return user;
    }

}
