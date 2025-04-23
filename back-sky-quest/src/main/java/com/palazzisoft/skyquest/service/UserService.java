package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.User;
import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public UserDTO findUserByEmail(UserDTO userDTO) {
        log.debug("Finding user by email {}", userDTO.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
        );

        User details = (User) userDetailsService.loadUserByUsername(userDTO.username());
        String token = authenticationService.generateToken(details);

        return UserDTO.builder()
                .token(token)
                .id(details.getId())
                .username(details.getUsername())
                .email(details.getEmail())
                .phone(details.getPhone())
                .id(details.getId())
                .build();
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        return mapper.map(userRepository.save(user), UserDTO.class);
    }
}
