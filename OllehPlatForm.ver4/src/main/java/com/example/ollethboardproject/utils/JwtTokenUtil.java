package com.example.ollethboardproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtTokenUtil {

    public static String createAccessToken(String userName, String key, Long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                // TODO: deprecated - signWith 원인 해결
                .signWith(SignatureAlgorithm.HS256, key)
//                .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)   이렇게하면 connnection 에러발생 // 박규현

                .compact();

    }

    public static String createRefreshToken(String userName, String key, Long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                // TODO: deprecated - signWith 원인 해결
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
    
    public static String getUserName(String token, String secretKey) {
        return extractClaim(token, secretKey).get("userName", String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        return extractClaim(token, secretKey).getExpiration().before(new Date());
    }

    private static Claims extractClaim(String token, String secretKey) {
        // TODO: deprecated - setSigningKey 원인 해결
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
