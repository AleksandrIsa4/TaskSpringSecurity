package org.example.springsecurityfirsthw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String examplePrivate() {
        return "Hello, authorized user!";
    }

}
