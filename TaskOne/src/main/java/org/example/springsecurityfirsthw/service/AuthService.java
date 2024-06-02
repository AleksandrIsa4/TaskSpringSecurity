package org.example.springsecurityfirsthw.service;

import org.example.springsecurityfirsthw.dto.Register;
import org.example.springsecurityfirsthw.exceptions.BadRequestException;
import org.example.springsecurityfirsthw.model.UserInfo;
import org.example.springsecurityfirsthw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public boolean register(Register register) {
        if (userRepository.findByUserName(register.getUserName()).isPresent()) {
            throw new BadRequestException("Пользователь с такми UserName существует");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(register.getUserName());
        userInfo.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(userInfo);
        return true;
    }

    public boolean login(String username, String password) {
        if (userRepository.findByUserName(username).orElse(null) == null) {
            return false;
        }
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        return encoder.matches(password, principal.getPassword());
    }
}
