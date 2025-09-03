package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.model.UserDTO;
import com.palazzisoft.skyquest.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("GET /user/me returns 200 and user when jwt cookie is present and valid")
    void getMe_WithValidCookie_ReturnsUser() throws Exception {
        // Arrange
        var user = UserDTO.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .phone("123456789")
                .token("token-abc")
                .build();

        Mockito.when(userService.findUserByToken(anyString())).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/user/me")
                        .cookie(new Cookie("jwt", "token-abc"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.token").value("token-abc"));
    }

    @Test
    @DisplayName("GET /user/me returns 401 when no jwt cookie is present")
    void getMe_WithoutCookie_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/user/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /user/me returns 401 when jwt cookie is present but user not found")
    void getMe_WithCookieButUserNotFound_ReturnsUnauthorized() throws Exception {
        Mockito.when(userService.findUserByToken(anyString())).thenReturn(null);

        mockMvc.perform(get("/user/me")
                        .cookie(new Cookie("jwt", "invalid-token"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
