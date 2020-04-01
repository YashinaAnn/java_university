package com.university.accounts.backend.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, Enum<?>> {

    private Set<Enum<?>> allowedValues;

    @Override
    public void initialize(SupportedCurrency constraintAnnotation) {
        allowedValues = Stream.of(constraintAnnotation.allowedValues()).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
