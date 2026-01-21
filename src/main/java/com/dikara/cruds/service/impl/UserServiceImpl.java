package com.dikara.cruds.service.impl;

import com.dikara.cruds.constant.GlobalMessage;
import com.dikara.cruds.constant.Status;
import com.dikara.cruds.dto.request.UserRequest;
import com.dikara.cruds.dto.response.PaginationResponse;
import com.dikara.cruds.dto.response.UserResponse;
import com.dikara.cruds.entity.User;
import com.dikara.cruds.exception.BusinessException;
import com.dikara.cruds.repository.UserRepository;
import com.dikara.cruds.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

private final UserRepository userRepository;

    @Override
    public List<UserResponse> findAll() {
       return userRepository.findAll().stream().map(this::mapToResponse).toList();

    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest user) {
        User userResult = userRepository.save(mapToEntity(user));
        return mapToResponse(userResult);
    }



    @Override
    public Map<String, String> deleteUser(String id) {
        User userResult = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (userResult != null) {
            userRepository.deleteById(Long.valueOf(id));
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User " + id + " has been deleted successfully");
            return response;
        } else {
            log.warn("User not found with code: {}", id);
            throw new BusinessException(
                    GlobalMessage.DATA_NOT_FOUND,
                    "user not found with id: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

    }

    @Override
    public UserResponse updateUser(String id, UserRequest user) {
        User userResult = userRepository.findById(Long.valueOf(id)).orElse(null);

        if (userResult == null){
            log.warn("User not found with code: {}", id);
            throw new BusinessException(
                    GlobalMessage.DATA_NOT_FOUND,
                    "user not found with id: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

       updateMapToEntity(user, userResult);
        userResult =userRepository.save(userResult);
        return mapToResponse(userResult);
    }

    @Override
    public PaginationResponse<UserResponse> findAll(int page, int size, String keyword) {
        int zeroBasedPage = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(zeroBasedPage, size);

        Page<User> centerPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            centerPage= userRepository.findByKeywordOrderByCreatedDateDesc(keyword, pageable);
        }else{
            centerPage =    userRepository.findByIsDeletedFalseOrderByCreatedDateDesc(pageable);
        }
        Page<UserResponse> mappedPage = centerPage.map(this::mapToResponse);
        return new PaginationResponse<>(mappedPage);
    }

    private UserResponse mapToResponse (User user){
        return UserResponse.builder()
                .userStatus(user.getUserStatus())
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .createdBy(user.getCreatedBy())
                .createdDate(user.getCreatedDate())
                .updatedBy(user.getUpdatedBy())
                .updatedDate(user.getUpdatedDate())
                .build();

    }

    private User mapToEntity (UserRequest userRequest){
        LocalDateTime now = LocalDateTime.now();
        String status =String.valueOf(Status.userStatus.ACTIVE);


        return User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .username(userRequest.getUsername())
                .userStatus(status)
                .createdBy("Admin")
                .updatedBy("Admin")
                .createdDate(now)
                .updatedDate(now)
                .build();
    }


    private void updateMapToEntity (UserRequest userRequest, User user){
        LocalDateTime now = LocalDateTime.now();

        user.setEmail(userRequest.getEmail());
        user.setUserStatus(userRequest.getStatus());
        user.setPassword(userRequest.getPassword());
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setUsername(userRequest.getUsername());
        user.setUpdatedBy("Admin");
        user.setUpdatedDate(now);




    }
}
