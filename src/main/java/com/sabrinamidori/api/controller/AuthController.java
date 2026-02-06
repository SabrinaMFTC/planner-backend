package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.domain.entity.user.User;
import com.sabrinamidori.api.dto.login.LoginRequest;
import com.sabrinamidori.api.dto.login.LoginResponse;
import com.sabrinamidori.api.dto.register.RegisterRequest;
import com.sabrinamidori.api.dto.register.RegisterResponse;
import com.sabrinamidori.api.infra.security.TokenService;
import com.sabrinamidori.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(request.name());
            newUser.setEmail(request.email());
            newUser.setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new RegisterResponse(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}
