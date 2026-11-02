package org.acme.catalog.user.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 32)
        String username,

        @NotBlank(message = "Display name is required")
        @Size(max = 32)
        String displayName
) {
}