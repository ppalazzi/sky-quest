package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        log.info("Login user with email {} ", userDTO.email());
        return ResponseEntity.ok(userService.findUserByEmail(userDTO));
    }
}
