package com.codigo.ms_login.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String extracUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraclaims, UserDetails userDetails);
    public boolean isTokenExpired2(String token);
}
