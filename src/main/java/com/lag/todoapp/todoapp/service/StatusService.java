package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.entity.StatusEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.StatusFilter;
import com.lag.todoapp.todoapp.model.request.StatusRequest;
import com.lag.todoapp.todoapp.model.response.StatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {
    Page<StatusDto> findFilteredAndPaginated(StatusFilter filters, Pageable pageable);

    StatusDto findById(Long statusId) throws NotFoundException;

    StatusDto create(StatusRequest request);

    StatusDto updateById(Long statusId, StatusRequest request) throws NotFoundException, ValidationErrorException;

    StatusDto deleteById(Long statusId) throws NotFoundException;

    List<StatusDto> findAll();
}
