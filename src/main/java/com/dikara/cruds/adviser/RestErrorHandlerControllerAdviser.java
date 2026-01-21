package com.dikara.cruds.adviser;


import com.dikara.cruds.constant.BaseResponse;
import com.dikara.cruds.constant.GlobalMessage;

import com.dikara.cruds.exception.BusinessException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dikara
 */

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class RestErrorHandlerControllerAdviser implements ErrorController {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> exception(Exception e) {
    log.warn("Exception = ", e);
    Map<String, Object> errors = new HashMap<>();
    errors.put("code", "INTERNAL_SERVER_ERROR");
    errors.put("message", "INTERNAL_SERVER_ERROR");
    return new ResponseEntity<>(BaseResponse.builder()
        .code(GlobalMessage.ERROR.code)
        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException manve) {

    log.error("MethodArgumentNotValidException = {}", manve.getMessage());

    List<FieldError> methodArgumentNotValidExceptionErrors = manve.getBindingResult()
        .getFieldErrors();

    Map<String, String> detail = new HashMap<>();
    List<String> messages = new ArrayList<>();
    for (FieldError fieldError : methodArgumentNotValidExceptionErrors) {
      detail.put(fieldError.getField(), fieldError.getDefaultMessage());
      messages.add(fieldError.getDefaultMessage());
    }

    String msg = ("Note! ").concat(messages.toString().replace("[", "").replace("]", ""));

    return new ResponseEntity<>(BaseResponse.builder()
        .code("400")
        .message(msg)
        .errors(detail)
        .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
  public ResponseEntity<Object> methodArgumentNotAllowed(HttpRequestMethodNotSupportedException manve) {
    log.info("Method Not Allowed = ", manve);

    Map<String, Object> er = new HashMap<>();
    er.put("code", "METHOD_ERROR");
    er.put("message", manve.getMessage().concat(", Please provide us using : " + manve.getSupportedHttpMethods()));
    return new ResponseEntity<>(BaseResponse.builder()
        .code(GlobalMessage.FAILED.code)
        .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
        .build(),
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> messageNotReadable(HttpMessageNotReadableException e) {
    log.warn("Exception:: messageNotReadable: {}", e.getMostSpecificCause().getMessage());

    Map<String, Object> err = new HashMap<>();
    Map<String, Object> detail = new HashMap<>();

    Throwable cause = e.getCause();

    if (cause instanceof JsonParseException) {
      JsonParseException jpe = (JsonParseException) cause;
      detail.put("requestError", jpe.getOriginalMessage());
    } else if (cause instanceof MismatchedInputException) {
      MismatchedInputException mie = (MismatchedInputException) cause;
      if (mie.getPath() != null && mie.getPath().size() > 0) {
        detail.put(mie.getPath().get(0).getFieldName(), "Mismatch input type");
      } else {
        detail.put("requestError", "Invalid request message");
      }
    } else if (cause instanceof JsonMappingException) {
      JsonMappingException jme = (JsonMappingException) cause;
      if (jme.getPath() != null && jme.getPath().size() > 0) {
        detail.put(jme.getPath().get(0).getFieldName(), jme.getOriginalMessage());
      }
    } else {
      detail.put("requestError", "Bad request");
    }

    err.put("code", "VALIDATION_ERROR");
    err.put("message", detail);
    return new ResponseEntity<>(BaseResponse.builder()
        .code(GlobalMessage.FAILED.code)
        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .build(),
        HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> resourceApiNotFound(NoResourceFoundException e) {
    log.warn("NoResourceFoundException = {}", e.getMessage());
    Map<String, Object> err = new HashMap<>();
    Map<String, Object> detail = new HashMap<>();
    detail.put("code", "RESOURCE_NOT_FOUND");
    detail.put("message", "Api not found");
    err.put("reason", detail);
    return new ResponseEntity<>(BaseResponse.builder()
        .code(GlobalMessage.DATA_NOT_FOUND.code)
        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
        .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> apiNotFound(NoHandlerFoundException e) {
    log.warn("NoHandlerFoundException = {}", e.getMessage());
    Map<String, Object> err = new HashMap<>();
    Map<String, Object> detail = new HashMap<>();
    detail.put("code", "RESOURCE_NOT_FOUND");
    detail.put("message", "Api not found");
    err.put("reason", detail);
    return new ResponseEntity<>(BaseResponse.builder()
        .code(GlobalMessage.DATA_NOT_FOUND.code)
        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
        .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> businessException(BusinessException e, final HttpServletRequest request) {
    return new ResponseEntity<>(BaseResponse.builder()
//        .errorField(null)
        .code(e.getCode())
        .message(e.getMessage())
        .build(),
        e.getHttpStatus());
  }

}
