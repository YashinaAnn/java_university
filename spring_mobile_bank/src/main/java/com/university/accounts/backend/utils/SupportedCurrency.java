package com.university.accounts.backend.utils;

import com.university.accounts.backend.model.Currency;
import org.jvnet.staxex.StAxSOAPBody;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = SupportedCurrencyValidator.class)
public @interface SupportedCurrency {

    Currency[] allowedValues() default {Currency.RUB};
    String message() default "must be any of {allowedValues}";
    Class<?>[] groups() default {};
    Class<? extends Json>[] payload() default {};
}
