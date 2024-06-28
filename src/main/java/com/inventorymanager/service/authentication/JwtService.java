package com.inventorymanager.service.authentication;

import com.inventorymanager.domain.supplier.Supplier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    public String generateToken(Supplier supplier){
        return Jwts
                .builder()
                .subject(supplier.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSigningKey())
                .compact();
    }
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public <T> T extractClaims(String token, Function<Claims, T> resolvers){
        Claims claims = extractAllClaims(token);
        return resolvers.apply(claims);

    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractClaims(token, Claims::getSubject);
        return username.equals(user.getUsername()) && !isTokenExpires(token);
    }

    private boolean isTokenExpires(String token) {
        Date expiredAt = extractClaims(token, Claims::getExpiration);
        return expiredAt.before(new Date());
    }
}
