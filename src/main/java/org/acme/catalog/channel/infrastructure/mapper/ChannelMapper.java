
package org.acme.catalog.channel.infrastructure.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.catalog.channel.application.dto.ChannelDTO;
import org.acme.catalog.channel.domain.Channel;
import org.acme.catalog.channel.infrastructure.persistence.ChannelEntity;

@ApplicationScoped
public class ChannelMapper {

    public ChannelEntity toEntity(Channel channel) {
        ChannelEntity entity = new ChannelEntity();

        entity.setDiscordId(channel.discordId());
        entity.setGuildDiscordId(channel.guildDiscordId());
        entity.setName(channel.name());
        entity.setType(channel.type());
        entity.setCreatedAt(channel.createdAt());

        return entity;
    }

    public Channel toDomain(ChannelEntity entity) {
        return Channel.of(
                entity.getDiscordId(),
                entity.getGuildDiscordId(),
                entity.getName(),
                entity.getType(),
                entity.getCreatedAt()
        );
    }

    public ChannelDTO toDTO(Channel channel) {
        return new ChannelDTO(
                channel.discordId(),
                channel.guildDiscordId(),
                channel.name(),
                channel.type(),
                channel.createdAt()
        );
    }
}
