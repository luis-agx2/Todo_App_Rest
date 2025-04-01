package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.UserFilter;
import com.lag.todoapp.todoapp.model.request.UserRequest;
import com.lag.todoapp.todoapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllFilteredAndPaginated(@RequestParam(required = false) String nickName,
                                                             @RequestParam(required = false) String email,
                                                             @RequestParam(required = false) String nameTerm,
                                                             @RequestParam(required = false) Set<Long> roleIds,
                                                             Pageable pageable) {
        UserFilter filter = new UserFilter(nickName, email, nameTerm, roleIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.findAll(filter, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getById(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateById(@Valid @RequestBody UserRequest request, @PathVariable Long userId) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(userService.updateById(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.deleteById(userId));
    }
}
