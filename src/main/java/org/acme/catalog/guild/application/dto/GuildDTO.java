
package org.acme.catalog.guild.application.dto;

import java.time.LocalDateTime;

public record GuildDTO(
        String discordId,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
