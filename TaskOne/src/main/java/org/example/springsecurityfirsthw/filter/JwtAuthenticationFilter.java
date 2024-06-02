package org.example.springsecurityfirsthw.filter;


import lombok.RequiredArgsConstructor;
import org.example.springsecurityfirsthw.exceptions.BadRequestException;
import org.example.springsecurityfirsthw.model.JwtAuthenticationToken;
import org.example.springsecurityfirsthw.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            if (!validateToken(token)) {
                throw new BadRequestException("Токен не совпадает");
            }
            Authentication authentication = createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
        }
        return token;
    }

    private boolean validateToken(String token) {
        return JwtUtil.validateToken(token, extractUserDetailsFromToken(token));
    }

    private Authentication createAuthentication(String token) {
        UserDetails userDetails = extractUserDetailsFromToken(token);
        return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private UserDetails extractUserDetailsFromToken(String token) {
        String userName = JwtUtil.extractUsername(token);
        return userDetailsService.loadUserByUsername(userName);
    }

}
