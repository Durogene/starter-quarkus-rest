package org.acme.catalog.guild.domain;

import java.time.LocalDateTime;

public record Guild(
        String discordId,
        String name,
        String description,
        LocalDateTime createdAt
) {
    public static Guild of(
            String discordId,
            String name,
            String description,
            LocalDateTime createdAt
    ) {
        return new Guild(
                discordId,
                name,
                description,
                createdAt
        );
    }
}