package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/details")
public class UserDetailController {
    private final UserDetailService userDetailService;

    @Autowired
    public UserDetailController (UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userDetailService.findByUserId(userId));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateByUserId(@RequestBody UserDetailsRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok(userDetailService.updateByUserId(userId, request));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMeDetails(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetailService.findByMe(userDetails));
    }

    @PutMapping("/me")
    public ResponseEntity<Object> updateMe(@RequestBody UserDetailsRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetailService.updateMe(request, userDetails));
    }
}
