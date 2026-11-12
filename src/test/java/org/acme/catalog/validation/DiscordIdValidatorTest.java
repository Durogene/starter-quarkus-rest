
package org.acme.catalog.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscordIdValidatorTest {

    private final DiscordIdValidator validator =
            new DiscordIdValidator();

    @Test
    void acceptsValidSnowflake() {
        assertTrue(
                validator.isValid("123456789012345678", null)
        );
    }

    @Test
    void rejectsInvalidSnowflakes() {
        assertFalse(validator.isValid(null, null));
        assertFalse(validator.isValid("", null));
        assertFalse(validator.isValid("123", null));
        assertFalse(
                validator.isValid("not-a-discord-id", null)
        );
    }
}
