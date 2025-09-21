package com.beatbloid.backend.filters;

import com.beatbloid.backend.utils.JwtUtil;
import com.beatbloid.backend.exceptions.UnauthorizedException;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Cookie jwtCookie = Arrays.stream(cookies)
                    .filter(cookie -> "access-token".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            if (jwtCookie != null) {
                String token = jwtCookie.getValue();

                if (jwtUtil.isTokenValid(token)) {
                    String username = jwtUtil.extractUserName(token);
                    request.setAttribute("user", username);  // Setting user in request attribute
                } else {
                    throw new UnauthorizedException("Invalid or expired JWT token.");
                }
            } else {
                throw new UnauthorizedException("JWT cookie not found. Please log in.");
            }
        } else {
            throw new UnauthorizedException("No cookies found. Please log in.");
        }

        filterChain.doFilter(request, response);
    }
}

