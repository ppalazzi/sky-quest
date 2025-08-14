package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        log.info("Login user with username {} ", userDTO.username());
        UserDTO userLogged = userService.findUserByUsername(userDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createCookie(userLogged.token()).toString())
                .body(userLogged);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        log.info("Logging out user");
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie().toString())
                .build();
    }

    private ResponseCookie deleteCookie() {
        return ResponseCookie.from("jwt", null)
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    private ResponseCookie createCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofHours(1))
                .build();
    }
}
