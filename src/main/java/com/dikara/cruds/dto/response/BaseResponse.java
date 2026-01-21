package com.dikara.cruds.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@Getter
@Setter
public class BaseResponse {


    private String createdBy;


    private LocalDateTime createdDate ;


    private String updatedBy;


    private LocalDateTime updatedDate;
}
