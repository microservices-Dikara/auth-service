package com.dikara.cruds.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Name Must Not Null")
    private String name;

    @NotBlank(message = "username Must Not Null")
    private String username;

    @NotBlank(message = "email Must Not Null")
    @jakarta.validation.constraints.Email(message = "Format email not valid")
    private String email;

    @NotBlank(message = "password Must Not Null")
    private String password;

    @NotBlank(message = "password Must Not Null")
    private String phoneNumber;

    private String status;
}
