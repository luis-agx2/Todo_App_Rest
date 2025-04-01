package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.UserMapper;
import com.lag.todoapp.todoapp.model.filter.UserFilter;
import com.lag.todoapp.todoapp.model.request.UserRequest;
import com.lag.todoapp.todoapp.model.response.UserDto;
import com.lag.todoapp.todoapp.repository.RoleRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import com.lag.todoapp.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<UserDto> findAll(UserFilter filter, Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAllFilteredAndPageable(
                filter.getNickName(),
                filter.getEmail(),
                filter.getNameTerm(),
                filter.getRoleIds(),
                pageable);

        return new PageImpl<>(
                userMapper.toDtoListWithRoles(userPage.getContent()),
                pageable,
                userPage.getTotalElements()
        );
    }

    @Override
    public UserDto findById(Long userId) throws NotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDtoWitOutRoles(user);
    }

    @Override
    public UserDto updateById(Long userId, UserRequest request) throws NotFoundException, ValidationErrorException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (existsNickname(request.getNickname(), user.getId())) {
            throw new ValidationErrorException("The nickname is already registered");
        }

        UserEntity userToUpdate = userMapper.toEntity(request, user);

        userToUpdate.setRoles(request.getRoleIds().isEmpty()
                ? user.getRoles()
                : getRoles(request.getRoleIds())
        );

        userToUpdate.setRoles(getRoles(request.getRoleIds()));
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        return userMapper.toDtoWitOutRoles(userRepository.save(userToUpdate));
    }

    @Override
    public UserDto deleteById(Long userId) throws NotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);

        return userMapper.toDtoWitOutRoles(user);
    }

    private boolean existsNickname(String name, Long userId) {
        return userRepository.findByNicknameAndIdIsNot(name, userId).isPresent();
    }

    private Set<RoleEntity> getRoles(Set<Long> roleIds) throws NotFoundException {
        Set<RoleEntity> roleEntities = new HashSet<>(roleRepository.findAllById(roleIds));

        if (roleEntities.size() < roleIds.size()) {
            throw new NotFoundException("Some role not was found");
        }

        return roleEntities;
    }
}
