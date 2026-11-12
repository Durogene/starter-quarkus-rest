
package org.acme.catalog.channel.domain;

import java.time.LocalDateTime;

public record Channel(
        String discordId,
        String guildDiscordId,
        String name,
        String type,
        LocalDateTime createdAt
) {
    public static Channel of(
            String discordId,
            String guildDiscordId,
            String name,
            String type,
            LocalDateTime createdAt
    ) {
        return new Channel(
                discordId,
                guildDiscordId,
                name,
                type,
                createdAt
        );
    }
}
