package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.UserFilter;
import com.lag.todoapp.todoapp.model.request.UserRequest;
import com.lag.todoapp.todoapp.model.response.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    Page<UserDto> findAll(UserFilter filter, Pageable pageable);

    UserDto findById(Long userId) throws NotFoundException;

    UserDto updateById(Long userId, UserRequest request) throws NotFoundException, ValidationErrorException;

    UserDto deleteById(Long userId) throws NotFoundException;
}
