package com.palazzisoft.skyquest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(antMatcher("/actuator/**")).permitAll()
                        .requestMatchers(antMatcher("/catalog/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(hb -> {
                });

        return http.build();
    }
}
