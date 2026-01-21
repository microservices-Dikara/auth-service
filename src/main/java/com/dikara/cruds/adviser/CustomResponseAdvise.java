package com.dikara.cruds.adviser;



import com.dikara.cruds.annotation.IgnoreResponseBinding;
import com.dikara.cruds.constant.BaseResponse;
import com.dikara.cruds.controller.BaseTransactionController;
import com.dikara.cruds.util.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import java.util.List;
import java.util.Map;
import java.util.Objects;



@RestControllerAdvice(basePackages = {"com.dikara.cruds.controller"})
@Slf4j
public final class CustomResponseAdvise extends BaseTransactionController implements ResponseBodyAdvice<Object> {

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)) {
            if (!Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBinding.class)) {

                if (o instanceof List || o instanceof Map) {
                    return buildSuccessResponse(o);
                } else if (o instanceof ResponseEntity<?>) {
                    log.info("Response is instance of response entity");
                    return o;
                } else if (o instanceof InputStreamResource) {
                    log.info("Response is instance of InputStreamResource");
                    return o;
                } else if (o instanceof byte[]) {
                    log.info("Response is instance of byte[]");
                    return o;
                } else if (o instanceof String) {
                    log.info("Response is instance of String[]");
                    return o;
                } else {
                    BaseResponse<?> baseResponse = BeanMapper.map(o, BaseResponse.class);
                    if (Objects.isNull(baseResponse.getCode()))
                        return buildSuccessResponse(o);
                    return o;
                }
            }
        }
        return o;
    }
}
