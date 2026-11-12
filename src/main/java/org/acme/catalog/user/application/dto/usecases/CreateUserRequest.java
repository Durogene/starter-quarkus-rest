
package org.acme.catalog.user.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.acme.catalog.validation.DiscordId;

public record CreateUserRequest(

        @DiscordId
        String discordId,

        @NotBlank(message = "Username is required")
        @Size(
                min = 2,
                max = 32,
                message = "Username must contain between 2 and 32 characters"
        )
        String username,

        @NotBlank(message = "Display name is required")
        @Size(
                max = 32,
                message = "Display name cannot exceed 32 characters"
        )
        String displayName
) {
}
