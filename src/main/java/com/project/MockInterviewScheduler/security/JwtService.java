package com.project.MockInterviewScheduler.security;

import com.project.MockInterviewScheduler.entity.CustomUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.secret}")
    private String jwtSecret ;

    public String extractUserName(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(CustomUser user){
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .claim("role", user.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setIssuedAt(new Date())
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Date extractExpiry(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token, CustomUser user){
        String tokenUsername = extractUserName(token);
        return tokenUsername.equals(user.getUsername()) && isTokenValid(token);
    }

    public boolean isTokenValid(String token) {
        Date tokenExpiry = extractExpiry(token);
        return tokenExpiry.after(new Date());
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }
}
