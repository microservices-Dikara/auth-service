package com.dikara.cruds.exception;


import com.dikara.cruds.constant.GlobalMessage;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessException extends RuntimeException {

    private String code;
    private String message;
    private HttpStatus httpStatus;

    public BusinessException(GlobalMessage globalMessage, HttpStatus httpStatus) {
        super(globalMessage.message);;
        this.code = globalMessage.code;
        this.message = globalMessage.message;
        this.httpStatus = httpStatus;
    }

    public BusinessException(GlobalMessage globalMessage, String additionalMessage, HttpStatus httpStatus) {
        super(globalMessage.message +" "+ additionalMessage);
        this.code = globalMessage.code;
        this.message = globalMessage.message +" "+ additionalMessage;
        this.httpStatus = httpStatus;
    }

    public BusinessException(GlobalMessage globalMessage, String additionalMessage) {
        super(globalMessage.message +" "+ additionalMessage);
        this.code = globalMessage.code;
        this.message = globalMessage.message +" "+ additionalMessage;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }


    @SneakyThrows
    public static void throwError(GlobalMessage globalMessage, String additionalMessage, HttpStatus httpStatus){
        throw new BusinessException(globalMessage, additionalMessage, httpStatus);
    }

    @SneakyThrows
    public static void throwError(GlobalMessage globalMessage, HttpStatus httpStatus){
        throw new BusinessException(globalMessage, httpStatus);
    }

}
