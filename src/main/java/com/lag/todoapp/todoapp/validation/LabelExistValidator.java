package com.lag.todoapp.todoapp.validation;

import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.repository.LabelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class LabelExistValidator implements ConstraintValidator<LabelExist, Object> {
    private final LabelRepository labelRepository;

    public LabelExistValidator(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public boolean isValid(Object name, ConstraintValidatorContext constraintValidatorContext) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return !labelRepository.existsByNameAndUserId((String) name, userDetails.getId());
    }
}
