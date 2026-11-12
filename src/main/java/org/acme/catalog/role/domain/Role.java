
package org.acme.catalog.role.domain;

import java.time.LocalDateTime;

public record Role(
        String discordId,
        String guildDiscordId,
        String name,
        LocalDateTime createdAt
) {
    public static Role of(
            String discordId,
            String guildDiscordId,
            String name,
            LocalDateTime createdAt
    ) {
        return new Role(discordId, guildDiscordId, name, createdAt);
    }
}
