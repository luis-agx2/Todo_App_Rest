package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/details")
public class UserDetailController {
    private final UserDetailService userDetailService;

    @Autowired
    public UserDetailController (UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userDetailService.findByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateByUserId(@RequestBody UserDetailsRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok(userDetailService.updateByUserId(userId, request));
    }
}
