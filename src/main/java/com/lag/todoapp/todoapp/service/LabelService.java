package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.LabelFilter;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LabelService {
    Page<LabelDto> findAllFilteredAndPaginated(LabelFilter filters, Pageable pageable);

    LabelDto findByIdAdmin(Long labelId) throws NotFoundException;

    LabelDto create(LabelRequest request) throws NotFoundException;

    LabelDto updateyId(LabelRequest request, Long labelId) throws NotFoundException, ValidationErrorException;

    LabelDto deleteById(Long labelId) throws NotFoundException;

    List<LabelDto> findAll();

    LabelDto findById(Long labelId) throws NotFoundException;
}
