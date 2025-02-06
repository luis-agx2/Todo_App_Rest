package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.request.LoginRequest;
import com.lag.todoapp.todoapp.model.request.RegisterRequest;
import com.lag.todoapp.todoapp.model.response.LoginDto;

public interface AuthService {
    LoginDto login(LoginRequest loginRequest);

    void register(RegisterRequest registerRequest) throws NotFoundException;
}
