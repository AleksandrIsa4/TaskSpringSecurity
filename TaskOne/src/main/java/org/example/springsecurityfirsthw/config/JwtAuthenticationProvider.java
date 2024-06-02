package org.example.springsecurityfirsthw.config;

import org.example.springsecurityfirsthw.model.JwtAuthenticationToken;
import org.example.springsecurityfirsthw.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = JwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (JwtUtil.validateToken(token, userDetails)) {
            return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("Неверный токен") {
            };
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
