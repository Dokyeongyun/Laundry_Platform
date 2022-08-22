package com.coders.laundry.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {
    private Set<String> enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(java.lang.Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return enumValues.contains(value.toString().toUpperCase());
    }
}
