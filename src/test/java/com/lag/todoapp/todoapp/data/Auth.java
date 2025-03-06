package com.lag.todoapp.todoapp.data;

import com.lag.todoapp.todoapp.model.request.LoginRequest;
import com.lag.todoapp.todoapp.model.request.RegisterRequest;

import java.util.HashSet;

public class Auth {
    public static RegisterRequest getRegisterRequest() {
        return new RegisterRequest(
                "email@test.com",
                "123456",
                "my_nick",
                new HashSet<>(),
                "Jose",
                "Perez",
                24);
    }

    public static LoginRequest getLoginRequest() {
        return new LoginRequest(
                "email@test.com",
                "123456"
        );
    }
}
