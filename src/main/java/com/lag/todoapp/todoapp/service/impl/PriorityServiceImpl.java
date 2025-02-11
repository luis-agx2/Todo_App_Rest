package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.PriorityEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.PriorityMapper;
import com.lag.todoapp.todoapp.model.filter.PriorityFilter;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.model.response.PriorityDto;
import com.lag.todoapp.todoapp.repository.PriorityRepository;
import com.lag.todoapp.todoapp.service.PriorityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepository priorityRepository;

    private final PriorityMapper priorityMapper;

    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository,
                               PriorityMapper priorityMapper) {
        this.priorityRepository = priorityRepository;
        this.priorityMapper = priorityMapper;
    }

    @Override
    public Page<PriorityDto> findAllFiltered(PriorityFilter filters, Pageable pageable) {
        Page<PriorityEntity> result = priorityRepository.findAllFilteredAndPaginated(filters.getName(), pageable);

        return new PageImpl<>(
                priorityMapper.toDtoList(result.getContent()),
                result.getPageable(),
                result.getTotalElements()
        );
    }

    @Override
    public PriorityDto findById(Long id) throws NotFoundException {
        PriorityEntity priority = priorityRepository.findById(id).orElseThrow(() -> new NotFoundException("Priority not found"));

        return priorityMapper.toDto(priority);
    }

    @Transactional
    @Override
    public PriorityDto create(PriorityRequest request) {
        PriorityEntity priorityToCreate = priorityMapper.toEntity(request);
        priorityToCreate.setCreatedAt(LocalDateTime.now());

        return priorityMapper.toDto(priorityRepository.save(priorityToCreate));
    }

    @Transactional
    @Override
    public PriorityDto updateById(Long priorityId, PriorityRequest request) throws NotFoundException, ValidationErrorException {
        PriorityEntity entity = priorityRepository.findById(priorityId).orElseThrow(() -> new NotFoundException("Priority not found"));

        if (priorityNameExist(request.getName(), entity.getId())) {
            throw new ValidationErrorException("This priority is already registered");
        }

        PriorityEntity priorityToEdit = priorityMapper.toEntity(request, entity);
        priorityToEdit.setUpdatedAt(LocalDateTime.now());

        return priorityMapper.toDto(priorityRepository.save(priorityToEdit));
    }

    @Transactional
    @Override
    public PriorityDto deleteById(Long priorityId) throws NotFoundException {
        PriorityEntity entity = priorityRepository.findById(priorityId).orElseThrow(() -> new NotFoundException("Priority not found"));

        priorityRepository.delete(entity);

        return priorityMapper.toDto(entity);
    }

    @Override
    public List<PriorityDto> findAll() {
        return priorityMapper.toDtoList(priorityRepository.findAll());
    }

    private boolean priorityNameExist(String priorityName, Long priorityId) {
        return priorityRepository.findByNameAndIdIsNot(priorityName, priorityId).isPresent();
    }
}
