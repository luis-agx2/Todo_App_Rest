package com.lag.todoapp.todoapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldExist {
    String message() default "This data is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entiy();

    String field();
}
