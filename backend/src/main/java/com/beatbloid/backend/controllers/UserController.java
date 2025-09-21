package com.beatbloid.backend.controllers;

import com.beatbloid.backend.dto.ResponseWrapper;
import com.beatbloid.backend.models.UserModel;
import com.beatbloid.backend.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<UserModel>> registerUser(@RequestBody UserModel user) {
        UserModel registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(new ResponseWrapper<UserModel>(200, "User registered successfully", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String emailOrUsername,
                                   @RequestParam String password,
                                   HttpServletResponse response) {
        String token = userService.loginUser(emailOrUsername, password);

        Cookie cookie = new Cookie("access-token", token);
        cookie.setHttpOnly(true); 
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60 * 7);

        response.addCookie(cookie);

        return ResponseEntity.ok(new ResponseWrapper<>(200, "Login successfull", null));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String emailOrUsername) {
        userService.sendPasswordResetLink(emailOrUsername);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Password reset link sent to email", null));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Password has been successfully reset", null));
    }
}
