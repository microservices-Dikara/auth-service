package com.dikara.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "m_refresh_token", schema = "master_param")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RefreshTokens extends BaseEntity {


    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_user")
    )
    private User user;

    @Column(nullable = false, length = 500)
    private String token;


    @Column(nullable = false)
    private boolean revoked = false;

    @Column(nullable = false)
    private Instant expiresAt;

    public RefreshTokens(UUID id, String accessToken, Instant plus, boolean b, User user) {

        this.id = id;
        this.token = accessToken;
        this.expiresAt = plus;
        this.revoked = b;
        this.user = user;

    }
}
