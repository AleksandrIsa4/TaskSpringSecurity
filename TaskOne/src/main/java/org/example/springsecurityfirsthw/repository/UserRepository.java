package org.example.springsecurityfirsthw.repository;

import org.example.springsecurityfirsthw.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserName(String userName);

}
