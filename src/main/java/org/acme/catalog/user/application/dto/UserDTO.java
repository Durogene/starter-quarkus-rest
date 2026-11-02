package org.acme.catalog.user.application.dto;

import java.time.LocalDateTime;

public record UserDTO(
        String discordId,
        String username,
        String displayName,
        LocalDateTime joinedAt
) {
}