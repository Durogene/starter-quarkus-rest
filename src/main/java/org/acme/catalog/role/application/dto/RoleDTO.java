
package org.acme.catalog.role.application.dto;

import java.time.LocalDateTime;

public record RoleDTO(
        String discordId,
        String guildDiscordId,
        String name,
        LocalDateTime createdAt
) {
}
