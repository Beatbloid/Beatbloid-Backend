package com.beatbloid.backend.services;

import com.beatbloid.backend.models.UserModel;
import com.beatbloid.backend.repositories.UserRepository;
import com.beatbloid.backend.utils.JwtUtil;
import com.beatbloid.backend.exceptions.UserAlreadyExistsException;
import com.beatbloid.backend.exceptions.UserNotFoundException;
import com.beatbloid.backend.exceptions.BadRequestException;
import com.beatbloid.backend.exceptions.UnauthorizedException;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserModel registerUser(UserModel user) {
        validateUserObject(user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists!");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists!");
        }
        if (userRepository.findByPhoneNumber(user
        .getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException("Phone number already exists!");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public String loginUser(String emailOrUsernameOrNumber, String password) {
        UserModel user = userRepository.findByEmail(emailOrUsernameOrNumber)
                .or(() -> userRepository.findByUsername(emailOrUsernameOrNumber))
                .or(() -> userRepository.findByPhoneNumber(emailOrUsernameOrNumber))
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    private void validateUserObject(UserModel user) {
        if (user.getEmail() == null && user.getUsername() == null && user.getPhoneNumber() == null) {
            throw new BadRequestException("You need either email/phone/username to register");
        }

        if (user.getPassword() == null) {
            throw new BadRequestException("User cannot be created without a password");
        }
    }

    public void sendPasswordResetLink(String emailOrUsername) {
        Optional<UserModel> userOpt = userRepository.findByEmail(emailOrUsername);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found with given email/username.");
        }

        UserModel user = userOpt.get();
        String token = JwtUtil.generateResetToken();
        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 30 * 60 * 1000));

        userRepository.save(user);

        String resetLink = "https://yourfrontend.com/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Reset Your Password", "Click this link to reset: " + resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        Optional<UserModel> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isEmpty() || userOpt.get().getResetTokenExpiry().before(new Date())) {
            throw new UnauthorizedException("Invalid or expired token.");
        }

        UserModel user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
    }

}
