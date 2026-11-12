
package org.acme.catalog.channel.application.dto;

import java.time.LocalDateTime;

public record ChannelDTO(
        String discordId,
        String guildDiscordId,
        String name,
        String type,
        LocalDateTime createdAt
) {
}
