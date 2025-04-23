package com.palazzisoft.skyquest.model;

import lombok.Builder;

@Builder
public record UserDTO(
        Long id,
        String username,
        String password,
        String email,
        String phone,
        String token
) {
}
