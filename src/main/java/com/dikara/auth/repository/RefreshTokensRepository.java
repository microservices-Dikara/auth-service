package com.dikara.auth.repository;

import com.dikara.auth.entity.RefreshTokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokensRepository extends JpaRepository<RefreshTokens, UUID> {


    Optional<RefreshTokens> findById(UUID uuid);

    @Modifying
    @Query("update RefreshTokens r set r.revoked = true where r.user = :userId")
    void revokeAllByUserId(UUID userId);

    Optional<RefreshTokens> findByToken(String s);
}
