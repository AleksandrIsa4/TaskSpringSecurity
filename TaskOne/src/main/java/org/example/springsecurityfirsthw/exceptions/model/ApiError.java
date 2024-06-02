package org.example.springsecurityfirsthw.exceptions.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    HttpStatus status;
    String reason;
    String message;
    String timestamp;
}