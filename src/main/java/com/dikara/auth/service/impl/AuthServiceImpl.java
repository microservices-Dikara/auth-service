package com.dikara.auth.service.impl;

import com.dikara.auth.constant.GlobalMessage;
import com.dikara.auth.dto.request.LoginRequest;
import com.dikara.auth.dto.request.RefreshRequest;
import com.dikara.auth.dto.response.LoginResponse;
import com.dikara.auth.entity.RefreshTokens;
import com.dikara.auth.entity.User;
import com.dikara.auth.exception.BusinessException;
import com.dikara.auth.repository.RefreshTokensRepository;
import com.dikara.auth.repository.UserRepository;
import com.dikara.auth.service.AuthService;
import com.dikara.auth.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RefreshTokensRepository  refreshRepo;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder encoder;
    @Override
    public LoginResponse login(LoginRequest req) {

        User user = userRepo.findByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPassword())) {
            log.warn("Invalid credentials: {}", req.username());
            throw new BusinessException(
                    GlobalMessage.INVALID_CREDENTIAL,
                    "Invalid credentials: " + req.username(),
                    HttpStatus.UNAUTHORIZED
            );

        }

        String accessToken =
                jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        UUID refreshTokenId = UUID.fromString(UUID.randomUUID().toString());



        refreshRepo.save(new RefreshTokens(
                refreshTokenId,
                accessToken,
                Instant.now().plus(7, ChronoUnit.DAYS),
                false,
                user
                ));
        return new LoginResponse(accessToken, String.valueOf(refreshTokenId));
    }

    @Override
    public LoginResponse refresh(RefreshRequest req) {

        RefreshTokens oldToken = refreshRepo.findByToken(req.refreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (oldToken.isRevoked() || oldToken.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        oldToken.setRevoked(true);

        User user = userRepo.findById(oldToken.getUser().getId())
                .orElseThrow();

        String newAccess =
                jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        UUID refreshTokenId = UUID.randomUUID();

        refreshRepo.save(new RefreshTokens(
                refreshTokenId,
                newAccess,
                Instant.now().plus(7, ChronoUnit.DAYS),
                false,
                user
        ));

        return new LoginResponse(newAccess, refreshTokenId.toString());
    }

    @Override
    public void logout(String refreshToken) {
        RefreshTokens token = refreshRepo.findByToken(refreshToken)
                .orElseThrow();
        token.setRevoked(true);

    }

    @Override
    public void logoutAll(UUID userId) {
        refreshRepo.revokeAllByUserId(userId);
    }
}
