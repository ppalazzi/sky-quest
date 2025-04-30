package com.palazzisoft.skyquest.filter;

import com.palazzisoft.skyquest.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TOKEN_PREFIX = "jwt";
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> cookies = returnJWTCookie(request.getCookies());

        if (cookies.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = cookies.get().getValue();
        final String userName = authenticationService.getUsernameFromJwtToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (nonNull(userName) || isNull(authentication)) {
            UserDetails details = userDetailsService.loadUserByUsername(userName);

            if (authenticationService.isTokenValid(token, details)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<Cookie> returnJWTCookie(Cookie[] cookies) {
        Optional<Cookie> optional = Optional.empty();
        if (nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtAuthenticationFilter.BEARER_TOKEN_PREFIX)) {
                    return Optional.of(cookie);
                }
            }
        }
        return optional;
    }
}

