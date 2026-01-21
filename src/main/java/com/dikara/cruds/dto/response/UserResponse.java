package com.dikara.cruds.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Getter
@Setter
public class UserResponse extends BaseResponse {
    private Long id;
    private String name;
    private String username;
    private String phoneNumber;
    private String userStatus;


}
