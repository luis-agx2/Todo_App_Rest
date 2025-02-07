package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.RoleFilter;
import com.lag.todoapp.todoapp.model.request.RoleRequest;
import com.lag.todoapp.todoapp.model.response.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    Page<RoleDto> findAllFiltered(RoleFilter filters, Pageable pageable);

    RoleDto findById(Long roleId) throws NotFoundException;

    RoleDto create(RoleRequest request);

    RoleDto update(Long roleId, RoleRequest request) throws NotFoundException, ValidationErrorException;

    RoleDto deleteById(Long roleId) throws NotFoundException;

    List<RoleDto> findAll();
}
