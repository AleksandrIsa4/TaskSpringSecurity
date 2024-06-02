package org.example.springsecurityfirsthw.service;

import org.example.springsecurityfirsthw.exceptions.DataNotFoundException;
import org.example.springsecurityfirsthw.model.UserInfo;
import org.example.springsecurityfirsthw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUserName(username).orElseThrow(()-> new DataNotFoundException("Такого пользователя нет"));
        return new User(userInfo.getUserName(), userInfo.getPassword(), new ArrayList<>());
    }

}

