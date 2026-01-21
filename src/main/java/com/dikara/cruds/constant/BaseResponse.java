package com.dikara.cruds.constant;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;
    private Map<String, String> errors;

}
