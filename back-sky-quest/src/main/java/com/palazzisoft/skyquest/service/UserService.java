package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.User;
import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public UserDTO findUserByUsername(UserDTO userDTO) {
        log.debug("Finding user by email {}", userDTO.username());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
        );

        User details = (User) userDetailsService.loadUserByUsername(userDTO.username());
        String token = authenticationService.generateToken(details);

        return buildUserDTO(token, details);
    }

    public UserDTO findUserByToken(String token) {
        String username  = authenticationService.getUsernameFromJwtToken(token);
        if (Objects.nonNull(username)) {
            User user = (User) userDetailsService.loadUserByUsername(username);
            return buildUserDTO(token, user);
        }
        return null;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    private static UserDTO buildUserDTO(String token, User details) {
        return UserDTO.builder()
                .token(token)
                .id(details.getId())
                .username(details.getUsername())
                .email(details.getEmail())
                .phone(details.getPhone())
                .id(details.getId())
                .build();
    }
}
