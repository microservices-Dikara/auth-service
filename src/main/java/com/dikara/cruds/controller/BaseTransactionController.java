package com.dikara.cruds.controller;


import com.dikara.cruds.constant.BaseResponse;
import com.dikara.cruds.constant.GlobalMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class BaseTransactionController {

  public static BaseResponse<?> buildSuccessResponse(Object data) {
    return BaseResponse.builder().code(GlobalMessage.SUCCESS.code).message(GlobalMessage.SUCCESS.message).data(data).build();
  }

}