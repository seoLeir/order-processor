package com.ecommerce.orderprocessor.security;

import com.ecommerce.orderprocessor.config.ApplicationJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Component
public class JwtTokenUtils {

    private final ApplicationJwtProperties jwtProperties;

    private final SecretKey secretKey;

    private final JwtParser parser;

    public JwtTokenUtils(ApplicationJwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes());
        parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String generateToken(String name, Collection<? extends GrantedAuthority> authorities){
        Map<String, Object> claims = new HashMap<>();

        List<String> roleList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);

        Date issDate = new Date(System.currentTimeMillis());
        Date expDate = new Date(issDate.getTime() + jwtProperties.getLifetime().toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(name)
                .issuedAt(issDate)
                .expiration(expDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims getAllClaimsFromJwtToken(String token){
        return parser.parseSignedClaims(token).getPayload();
    }

    public String getUsername(String token){
        return getAllClaimsFromJwtToken(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token){
        return getAllClaimsFromJwtToken(token)
                .get("roles", List.class);
    }
}
