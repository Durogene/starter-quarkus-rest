
package org.acme.catalog.guild.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGuildRequest(

        @NotBlank
        @Size(min = 17, max = 20)
        String discordId,

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 1000)
        String description
) {
}
