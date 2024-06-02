package org.example.springsecurityfirsthw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private static long TIME_LIFE=1000000;

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIME_LIFE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractAllClaimsForToken(token).getSubject();
    }

    public static Date extractExpiration(String token) {
        return extractAllClaimsForToken(token).getExpiration();
    }

    private static Claims extractAllClaimsForToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
