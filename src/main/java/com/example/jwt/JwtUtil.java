package com.example.jwt;

import java.security.Key;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

    private static final String SECRET =
    		"b211fadb54afcdece91d916d6ddaf6e0037a692c49aa62bf0e7ec859dc52412d931127a32ece457ba680483bdcbd3ece963d1029b23e0ac56c1bb22e20b1c3f0";
    private static final long EXPIRATION =
            1000 * 60 * 60;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId,
            String email,
            Long orgId,
            List<String> roles) {

				return Jwts.builder()
				.subject(email)
				.claim("userId", userId)
				.claim("orgId", orgId)
				.claim("roles", roles)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSigningKey())
				.compact();
				}
    
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public String extractEmail(String token) {
        return extractAllClaims(token)
        		.getSubject();
    }

    public Long extractUserId(String token) {
    	Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    public Long extractOrgId(String token) {
    	Claims claims = extractAllClaims(token);
        return claims.get("orgId", Long.class);
    }

    public List<String> extractRoles(String token) {
    	Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }


    public boolean isTokenValid(String token) {
        Claims claims = extractAllClaims(token);
        return !claims.getExpiration().before(new Date());
    }

   
}
