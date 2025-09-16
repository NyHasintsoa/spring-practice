package com.exercise.project.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.exercise.project.validation.validator.EqualToValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualToValidator.class)
@Documented
public @interface EqualTo {
    String message() default "CONFIRM_NOT_EQUAL_VALIDATION";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String propertyPath();
}