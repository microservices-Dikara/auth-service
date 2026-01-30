package com.dikara.auth.controller;

import com.dikara.auth.dto.request.LoginRequest;
import com.dikara.auth.dto.request.RefreshRequest;
import com.dikara.auth.dto.response.LoginResponse;
import com.dikara.auth.dto.response.UserResponse;
import com.dikara.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication management API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login Api")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @Operation(summary = "Refresh Token")
    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshRequest req) {
        return authService.refresh(req);
    }

    @Operation(summary = "Logout Api")
    @PostMapping("/logout")
    public void logout(@RequestBody RefreshRequest req) {
        authService.logout(req.refreshToken());
    }

    @Operation(summary = "get profile Api")
    @GetMapping("/me")
    public UserResponse me(
            @RequestHeader("X-User-Id") String userId
    ) {
        return  authService.getProfile(userId);
    }
}
