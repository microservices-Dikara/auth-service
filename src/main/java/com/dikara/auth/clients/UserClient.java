package com.dikara.auth.clients;

import com.dikara.auth.constant.BaseResponse;
import com.dikara.auth.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/me")
    UserResponse getCurrentUser(
            @RequestHeader("Authorization") String token
    );
}
