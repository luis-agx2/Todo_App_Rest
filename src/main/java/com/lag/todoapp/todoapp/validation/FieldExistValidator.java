package com.lag.todoapp.todoapp.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldExistValidator implements ConstraintValidator<FieldExist, Object> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entiy;

    private String field;

    @Override
    public void initialize(FieldExist constraintAnnotation) {
        this.entiy = constraintAnnotation.entiy();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String queryString = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value", entiy.getSimpleName(), field);

        Query query = entityManager.createQuery(queryString);
        query.setParameter("value", value);

        Long count = (Long) query.getSingleResult();

        return count == 0;
    }
}
