package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.LabelEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.LabelMapper;
import com.lag.todoapp.todoapp.mapper.UserMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.LabelFilter;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import com.lag.todoapp.todoapp.repository.LabelRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import com.lag.todoapp.todoapp.service.LabelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    private final LabelMapper labelMapper;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository,
                            UserRepository userRepository,
                            LabelMapper labelMapper) {
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
        this.labelMapper = labelMapper;
    }

    @Override
    public Page<LabelDto> findAllFilteredAndPaginated(LabelFilter filters, Pageable pageable) {
        Page<LabelEntity> result = labelRepository.findAllFilteredAndPaginated(filters.getName(), filters.getColor(), pageable);

        return new PageImpl<>(
                labelMapper.toDtosListAdmin(result.getContent()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    public LabelDto findByIdAdmin(Long labelId) throws NotFoundException {
        LabelEntity entity = labelRepository.findById(labelId).orElseThrow(() -> new NotFoundException("Label not found"));

        return labelMapper.toDtoAdmin(entity);
    }

    @Transactional
    @Override
    public LabelDto create(LabelRequest request, CustomUserDetails userDetails) throws NotFoundException {
        LabelEntity labelToCreate = mapToEntity(request, userDetails.getId());
        labelToCreate.setCreatedAt(LocalDateTime.now());

        return labelMapper.toDto(labelRepository.save(labelToCreate));
    }

    @Transactional
    @Override
    public LabelDto updateyId(LabelRequest request, Long labelId, CustomUserDetails userDetails) throws NotFoundException, ValidationErrorException {
        LabelEntity label = labelRepository.findByIdAndUserId(labelId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Label not found"));

        if (labelNameExists(request.getName(), userDetails.getId(), label.getId())) {
            throw new ValidationErrorException("Label is already registered");
        }

        LabelEntity labelToEdit = labelMapper.toEntity(request, label);
        labelToEdit.setUpdatedAt(LocalDateTime.now());

        return labelMapper.toDto(labelRepository.save(labelToEdit));
    }

    @Override
    public LabelDto deleteById(Long labelId, CustomUserDetails userDetails) throws NotFoundException {
        LabelEntity label = labelRepository.findByIdAndUserId(labelId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Label not found"));

        labelRepository.delete(label);

        return labelMapper.toDto(label);
    }

    @Override
    public List<LabelDto> findAll(CustomUserDetails userDetails) {
        return labelMapper.toDtosList(labelRepository.findAllByUserId(userDetails.getId()));
    }

    @Override
    public LabelDto findById(Long labelId, CustomUserDetails userDetails) throws NotFoundException {
        LabelEntity entity = labelRepository.findByIdAndUserId(labelId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Label not found"));

        return labelMapper.toDto(entity);
    }

    private LabelEntity mapToEntity(LabelRequest labelRequest, Long userId) throws NotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        LabelEntity entity = labelMapper.toEntity(labelRequest);
        entity.setUser(user);

        return entity;
    }

    private boolean labelNameExists(String name, Long userId, Long labelId) {
        System.out.println(labelRepository.findByNameAndUserIdAndIdIsNot(name, userId, labelId).isPresent());
        return labelRepository.findByNameAndUserIdAndIdIsNot(name, userId, labelId).isPresent();
    }
}
