package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.model.response.UserDetailsDto;

public interface UserDetailService {
    UserDetailsDto findByUserId(Long userId) throws NotFoundException;

    UserDetailsDto updateByUserId(Long userId, UserDetailsRequest request) throws NotFoundException;

    UserDetailsDto findByMe(CustomUserDetails userDetails) throws NotFoundException;

    UserDetailsDto updateMe(UserDetailsRequest request, CustomUserDetails userDetails) throws NotFoundException;
}
