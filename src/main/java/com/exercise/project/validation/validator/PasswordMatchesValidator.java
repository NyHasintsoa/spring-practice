package com.exercise.project.validation.validator;

import java.lang.reflect.Field;

import com.exercise.project.validation.constraints.PasswordMatches;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String passwordFieldName;
    private String confirmPasswordFieldName;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        passwordFieldName = constraintAnnotation.password();
        confirmPasswordFieldName = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext conext) {
        try {
            Object password = getFieldValue(value, passwordFieldName);
            Object confirmPassword = getFieldValue(value, confirmPasswordFieldName);

            if (password == null) {
                return confirmPassword == null;
            }

            return password.equals(confirmPassword);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

}
