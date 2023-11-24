package com.romys.services;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;

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

    @Autowired
    private UserService service;

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
        return this.extractClaim(this.filter(token), Claims::getSubject);
    }

    public ElasticHit<UserModel> getUser(String token) throws IOException {
        return this.service.getUserByUsername(this.extractUsername(token));
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(this.extractAllClaims(token));
    }

    private Boolean isExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public Boolean isValid(String token, UserDetails user) {
        return this.extractUsername(token).equals(user.getUsername()) && this.isExpired(token) == false;
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

    private String filter(String token) {
        if (token.startsWith("Bearer"))
            token = token.substring(7);

        return token;
    }
}
