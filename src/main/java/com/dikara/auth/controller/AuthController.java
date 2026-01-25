package com.dikara.auth.controller;

import com.dikara.auth.dto.request.LoginRequest;
import com.dikara.auth.dto.request.RefreshRequest;
import com.dikara.auth.dto.response.LoginResponse;
import com.dikara.auth.dto.response.UserResponse;
import com.dikara.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshRequest req) {
        return authService.refresh(req);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshRequest req) {
        authService.logout(req.refreshToken());
    }

    @GetMapping("/me")
    public UserResponse me(
            @RequestHeader("Authorization") String token
    ) {
        return  authService.getProfile(token);
    }
}
