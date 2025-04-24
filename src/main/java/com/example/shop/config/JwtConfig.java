package com.example.shop.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
@Data
public class JwtConfig {

    @Value("${jwt.jwtKey}")
    private String jwtKeyStr;
    private Key jwtKey;
    @Value("${jwt.jwtExpired}")
    private Integer jwtExpired;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtKeyStr);
        this.jwtKey = Keys.hmacShaKeyFor(keyBytes);
    }
}
