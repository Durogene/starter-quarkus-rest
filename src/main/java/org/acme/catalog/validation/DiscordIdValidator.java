package org.acme.catalog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DiscordIdValidator
        implements ConstraintValidator<DiscordId, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {

        if (value == null || value.isBlank()) {
            return false;
        }

        return value.matches("\\d{17,20}");
    }
}