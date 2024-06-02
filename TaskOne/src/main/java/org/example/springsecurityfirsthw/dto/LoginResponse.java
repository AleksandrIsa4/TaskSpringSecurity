package org.example.springsecurityfirsthw.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginResponse {
    String token;

    String roles;
}
