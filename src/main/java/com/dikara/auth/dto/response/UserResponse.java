package com.dikara.auth.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends DefaultResponse {
    private UUID id;
    private String name;
    private String username;
    private String phoneNumber;
    private String userStatus;
    private String role;
}
