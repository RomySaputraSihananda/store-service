package com.romys.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${service.jwt.SECRET}")
    private String SECRET;

    @Value("${service.jwt.EXPIRATION}")
    private int EXPIRATION;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        return this.generateClaimsToken(username, claims);
    }

    private String generateClaimsToken(String username, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.EXPIRATION))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(this.extractAllClaims(token));
    }

    private Boolean isExpired(String token) {
        return (this.extractExpiration(token).before(new Date()));
    }

    public Boolean isValid(String token, UserDetails user) {
        return (this.extractUsername(token).equals(user.getUsername()) && this.isExpired(token) == false);
    }

    private Claims extractAllClaims(String token) {
        return (Jwts
                .parserBuilder()
                .setSigningKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody());
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
