package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.RoleMapper;
import com.lag.todoapp.todoapp.model.filter.RoleFilter;
import com.lag.todoapp.todoapp.model.request.RoleRequest;
import com.lag.todoapp.todoapp.model.response.RoleDto;
import com.lag.todoapp.todoapp.repository.RoleRepository;
import com.lag.todoapp.todoapp.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final String ROLE_NOT_FOUND_MESSAGE = "Role not found";

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Page<RoleDto> findAllFiltered(RoleFilter filters, Pageable pageable) {
        Page<RoleEntity> page = roleRepository.findAllFiltered(filters.getName(), filters.getCreatedAt(), pageable);

        return new PageImpl<>(
                roleMapper.toDtoList(page.getContent()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public RoleDto findById(Long roleId) throws NotFoundException {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND_MESSAGE));

        return roleMapper.toDto(role);
    }

    @Transactional
    @Override
    public RoleDto create(RoleRequest request) {
        RoleEntity roleToSave = roleMapper.toEntity(request);
        roleToSave.setCreatedAt(LocalDateTime.now());

        return roleMapper.toDto(roleRepository.save(roleToSave));
    }

    @Override
    public RoleDto update(Long roleId, RoleRequest request) throws NotFoundException, ValidationErrorException {
            RoleEntity role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND_MESSAGE));

            if (roleNameExists(request.getName(), roleId)) {
                throw new ValidationErrorException("This role is already registered.");
            }

            RoleEntity roleToEdit = roleMapper.toEntity(request, role);
            roleToEdit.setUpdatedAt(LocalDateTime.now());

            return roleMapper.toDto(roleRepository.save(roleToEdit));
    }

    @Override
    public RoleDto deleteById(Long roleId) throws NotFoundException {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND_MESSAGE));

        roleRepository.delete(role);

        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    private boolean roleNameExists(String name, Long roleId) {
        return roleRepository.findByNameAndIdIsNot(name, roleId).isPresent();
    }
}
