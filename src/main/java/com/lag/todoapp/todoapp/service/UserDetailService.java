package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.model.response.UserDetailsDto;

public interface UserDetailService {
    UserDetailsDto findByUserId(Long userId) throws NotFoundException;

    UserDetailsDto updateByUserId(Long userId, UserDetailsRequest request) throws NotFoundException;
}
