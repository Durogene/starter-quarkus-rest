
package org.acme.catalog.channel.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import org.acme.catalog.channel.application.dto.ChannelDTO;
import org.acme.catalog.channel.application.dto.usecases.CreateChannelRequest;
import org.acme.catalog.channel.application.dto.usecases.UpdateChannelRequest;
import org.acme.catalog.channel.domain.Channel;
import org.acme.catalog.channel.infrastructure.mapper.ChannelMapper;
import org.acme.catalog.channel.infrastructure.persistence.ChannelEntity;
import org.acme.catalog.channel.infrastructure.persistence.ChannelRepository;
import org.acme.catalog.guild.infrastructure.persistence.GuildRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ChannelService {

    private final ChannelRepository repository;
    private final ChannelMapper mapper;
    private final GuildRepository guildRepository;

    public ChannelService(
            ChannelRepository repository,
            ChannelMapper mapper,
            GuildRepository guildRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guildRepository = guildRepository;
    }

    @Transactional
    public ChannelDTO create(CreateChannelRequest request) {
        if (repository.existsByDiscordId(request.discordId())) {
            throw new WebApplicationException(
                    "Channel already exists", 409
            );
        }

        if (!guildRepository.existsByDiscordId(request.guildDiscordId())) {
            throw new NotFoundException("Guild not found");
        }

        Channel channel = Channel.of(
                request.discordId(),
                request.guildDiscordId(),
                request.name(),
                request.type(),
                LocalDateTime.now()
        );

        repository.persist(mapper.toEntity(channel));
        return mapper.toDTO(channel);
    }

    public List<ChannelDTO> findAll() {
        return findAll(0, 20, null);
    }

    public List<ChannelDTO> findAll(
            int page, int size, String name
    ) {
        if (page < 0 || size < 1 || size > 100) {
            throw new BadRequestException(
                    "Page must be >= 0 and size between 1 and 100"
            );
        }

        return repository.findPage(page, size, name)
                .stream()
                .map(mapper::toDomain)
                .map(mapper::toDTO)
                .toList();
    }

    public ChannelDTO findByDiscordId(String discordId) {
        ChannelEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Channel not found")
                );

        return mapper.toDTO(mapper.toDomain(entity));
    }

    @Transactional
    public ChannelDTO update(
            String discordId,
            UpdateChannelRequest request
    ) {
        ChannelEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Channel not found")
                );

        entity.setName(request.name());
        entity.setType(request.type());

        return mapper.toDTO(mapper.toDomain(entity));
    }

    @Transactional
    public void delete(String discordId) {
        ChannelEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Channel not found")
                );

        repository.delete(entity);
    }
}
