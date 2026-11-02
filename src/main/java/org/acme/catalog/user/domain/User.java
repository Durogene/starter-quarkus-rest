package org.acme.catalog.user.domain;

import java.time.LocalDateTime;

public record User(
        String discordId,
        String username,
        String displayName,
        LocalDateTime joinedAt
) {

    public User {
        if (discordId == null || discordId.isBlank()) {
            throw new IllegalArgumentException(
                    "Discord ID cannot be null or empty"
            );
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username cannot be null or empty"
            );
        }

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException(
                    "Display name cannot be null or empty"
            );
        }

        if (joinedAt == null) {
            throw new IllegalArgumentException(
                    "Joined date cannot be null"
            );
        }
    }

    public static User of(
            String discordId,
            String username,
            String displayName,
            LocalDateTime joinedAt
    ) {
        return new User(discordId, username, displayName, joinedAt);
    }
}