package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.UserDetailEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.mapper.UserDetailMapper;
import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.model.response.UserDetailsDto;
import com.lag.todoapp.todoapp.repository.UserDetailRepository;
import com.lag.todoapp.todoapp.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private final UserDetailRepository userDetailRepository;

    private final UserDetailMapper userDetailMapper;

    @Autowired
    public UserDetailServiceImpl(UserDetailRepository userDetailRepository,
                                 UserDetailMapper userDetailMapper) {
        this.userDetailRepository = userDetailRepository;
        this.userDetailMapper = userDetailMapper;
    }


    @Override
    public UserDetailsDto findByUserId(Long userId) throws NotFoundException {
        UserDetailEntity userDetail = userDetailRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));

        return userDetailMapper.toDto(userDetail);
    }

    @Override
    public UserDetailsDto updateByUserId(Long userId, UserDetailsRequest request) throws NotFoundException {
        UserDetailEntity userDetail = userDetailRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));
        UserDetailEntity detailsToUpdate = userDetailMapper.toEntity(request, userDetail);
        detailsToUpdate.setUser(userDetail.getUser());
        detailsToUpdate.setUpdatedAt(LocalDateTime.now());

        return userDetailMapper.toDto(userDetailRepository.save(detailsToUpdate));
    }
}
