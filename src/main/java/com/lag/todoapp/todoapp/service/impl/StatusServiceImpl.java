package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.StatusEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.StatusMapper;
import com.lag.todoapp.todoapp.model.filter.StatusFilter;
import com.lag.todoapp.todoapp.model.request.StatusRequest;
import com.lag.todoapp.todoapp.model.response.StatusDto;
import com.lag.todoapp.todoapp.repository.StatusRepository;
import com.lag.todoapp.todoapp.service.StatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusServiceImpl(StatusRepository statusRepository,
                             StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    @Override
    public Page<StatusDto> findFilteredAndPaginated(StatusFilter filters, Pageable pageable) {
        Page<StatusEntity> result = statusRepository.findAllFilteredAndPaginated(filters.getName(), pageable);

        return new PageImpl<>(
                statusMapper.toEntitiesList(result.getContent()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    public StatusDto findById(Long statusId) throws NotFoundException {
        StatusEntity entity = statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Status not found"));

        return statusMapper.toDto(entity);
    }

    @Transactional
    @Override
    public StatusDto create(StatusRequest request) {
        StatusEntity statusToCreate = statusMapper.toEntity(request);
        statusToCreate.setCreatedAt(LocalDateTime.now());

        return statusMapper.toDto(statusRepository.save(statusToCreate));
    }

    @Transactional
    @Override
    public StatusDto updateById(Long statusId, StatusRequest request) throws NotFoundException, ValidationErrorException {
        StatusEntity entity = statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Status not found"));

        if (existsStatusName(request.getName(), entity.getId())) {
            throw new ValidationErrorException("This status is already registered");
        }

        StatusEntity statusToEdit = statusMapper.toEntity(request, entity);
        statusToEdit.setUpdatedAt(LocalDateTime.now());

        return statusMapper.toDto(statusRepository.save(statusToEdit));
    }

    @Override
    public StatusDto deleteById(Long statusId) throws NotFoundException {
        StatusEntity entity = statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Status not found"));

        statusRepository.delete(entity);

        return statusMapper.toDto(entity);
    }

    @Override
    public List<StatusDto> findAll() {
        return statusMapper.toEntitiesList(statusRepository.findAll());
    }

    private boolean existsStatusName(String name, Long statusId) {
        return statusRepository.findByNameAndIdIsNot(name, statusId).isPresent();
    }
}
