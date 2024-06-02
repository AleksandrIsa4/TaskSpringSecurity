package org.example.springsecurityfirsthw.controller;

import org.example.springsecurityfirsthw.dto.LoginResponse;
import org.example.springsecurityfirsthw.dto.Login;
import org.example.springsecurityfirsthw.dto.Register;
import org.example.springsecurityfirsthw.service.AuthService;
import org.example.springsecurityfirsthw.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody @Valid @NotNull Register register) {
        authService.register(register);
        return "Пользователь зарегестрирован";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid @NotNull Login login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUserName());
        String token = jwtUtil.generateToken(userDetails);
        return LoginResponse.builder()
                .token(token)
                .roles(userDetails.getAuthorities().toString())
                .build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}

