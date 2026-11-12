
package org.acme.catalog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DiscordIdValidator.class)
public @interface DiscordId {

    String message() default "Invalid Discord ID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
