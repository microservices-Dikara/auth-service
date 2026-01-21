package com.dikara.cruds.service;

import com.dikara.cruds.dto.request.UserRequest;
import com.dikara.cruds.dto.response.PaginationResponse;
import com.dikara.cruds.dto.response.UserResponse;


import java.util.List;
import java.util.Map;


public interface UserService {

    List<UserResponse> findAll();
    UserResponse createUser(UserRequest user);
    Map<String, String> deleteUser(String id);
    UserResponse updateUser(String id, UserRequest user);
    PaginationResponse<UserResponse> findAll(int page, int size, String keyword);

}
