package com.dikara.auth.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Getter
@Setter
public class UserResponse extends BaseResponse {
    private UUID id;
    private String name;
    private String username;
    private String phoneNumber;
    private String userStatus;


}
