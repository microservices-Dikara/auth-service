package com.dikara.auth.service;

import com.dikara.auth.dto.request.LoginRequest;
import com.dikara.auth.dto.request.RefreshRequest;
import com.dikara.auth.dto.response.LoginResponse;

import java.util.UUID;

public interface AuthService {
    public LoginResponse login(LoginRequest req);
    public LoginResponse refresh(RefreshRequest req);
    public void logout(String refreshToken);
    public void logoutAll(UUID userId);

}
