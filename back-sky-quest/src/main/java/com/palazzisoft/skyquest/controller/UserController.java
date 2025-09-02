package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static final String JWT_COOKIE_NAME = "jwt";
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

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserByToken(@CookieValue(value = JWT_COOKIE_NAME, required = false) String token) {
        log.info("who am I?");

        if (Objects.nonNull(token) && !token.isBlank()) {
            UserDTO user = userService.findUserByToken(token);
            if (Objects.nonNull(user)) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
        return ResponseCookie.from(JWT_COOKIE_NAME, token)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofHours(1))
                .build();
    }
}
