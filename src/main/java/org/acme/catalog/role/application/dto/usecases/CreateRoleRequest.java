
package org.acme.catalog.role.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRoleRequest(

        @NotBlank
        @Pattern(regexp = "[0-9]{17,20}")
        String discordId,

        @NotBlank
        @Pattern(regexp = "[0-9]{17,20}")
        String guildDiscordId,

        @NotBlank
        @Size(max = 100)
        String name
) {
}
