package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.PriorityFilter;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.model.response.PriorityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriorityService {
    Page<PriorityDto> findAllFiltered(PriorityFilter filters, Pageable pageable);

    PriorityDto findById(Long id) throws NotFoundException;

    PriorityDto create(PriorityRequest request);

    PriorityDto updateById(Long priorityId, PriorityRequest request) throws NotFoundException, ValidationErrorException;

    PriorityDto deleteById(Long priorityId) throws NotFoundException;

    List<PriorityDto> findAll();
}
