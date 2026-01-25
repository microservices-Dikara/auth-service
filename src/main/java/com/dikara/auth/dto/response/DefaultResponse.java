package com.dikara.auth.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse {


    private String createdBy;


    private LocalDateTime createdDate ;


    private String updatedBy;


    private LocalDateTime updatedDate;
}
