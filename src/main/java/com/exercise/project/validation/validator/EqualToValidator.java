package com.exercise.project.validation.validator;

import java.lang.reflect.Field;

import com.exercise.project.validation.constraints.EqualTo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EqualToValidator implements ConstraintValidator<EqualTo, Object> {

    private String propertyPath;

    @Override
    public void initialize(EqualTo constraintAnnotation) {
        propertyPath = constraintAnnotation.propertyPath();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        try {
            Object rootObject = getRootObject(context);
            if (rootObject == null) {
                return false;
            }
            Object comparisonValue = getFieldValue(rootObject, propertyPath);
            if (comparisonValue == null) {
                return value == null;
            }

            return comparisonValue.equals(value);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getRootObject(ConstraintValidatorContext context) {
        try {
            Field field = context.getClass().getDeclaredField("validatedValue");
            field.setAccessible(true);
            return field.get(context);
        } catch (Exception e) {
            return null;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

}
