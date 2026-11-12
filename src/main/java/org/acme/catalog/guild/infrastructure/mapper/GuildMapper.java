
package org.acme.catalog.guild.infrastructure.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.catalog.guild.application.dto.GuildDTO;
import org.acme.catalog.guild.domain.Guild;
import org.acme.catalog.guild.infrastructure.persistence.GuildEntity;

@ApplicationScoped
public class GuildMapper {

    public GuildEntity toEntity(Guild guild) {

        GuildEntity entity = new GuildEntity();

        entity.setDiscordId(guild.discordId());
        entity.setName(guild.name());
        entity.setDescription(guild.description());
        entity.setCreatedAt(guild.createdAt());

        return entity;
    }

    public Guild toDomain(GuildEntity entity) {

        return Guild.of(
                entity.getDiscordId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public GuildDTO toDTO(Guild guild) {

        return new GuildDTO(
                guild.discordId(),
                guild.name(),
                guild.description(),
                guild.createdAt()
        );
    }
}
