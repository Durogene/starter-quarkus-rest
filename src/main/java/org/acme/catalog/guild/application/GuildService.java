
package org.acme.catalog.guild.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import org.acme.catalog.guild.application.dto.GuildDTO;
import org.acme.catalog.guild.application.dto.usecases.CreateGuildRequest;
import org.acme.catalog.guild.application.dto.usecases.UpdateGuildRequest;
import org.acme.catalog.guild.domain.Guild;
import org.acme.catalog.guild.infrastructure.mapper.GuildMapper;
import org.acme.catalog.guild.infrastructure.persistence.GuildEntity;
import org.acme.catalog.guild.infrastructure.persistence.GuildRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class GuildService {

    private final GuildRepository guildRepository;
    private final GuildMapper guildMapper;

    public GuildService(
            GuildRepository guildRepository,
            GuildMapper guildMapper
    ) {
        this.guildRepository = guildRepository;
        this.guildMapper = guildMapper;
    }

    @Transactional
    public GuildDTO create(CreateGuildRequest request) {

        if (guildRepository.existsByDiscordId(request.discordId())) {
            throw new BadRequestException(
                    "A guild with this Discord ID already exists"
            );
        }

        Guild guild = Guild.of(
                request.discordId(),
                request.name(),
                request.description(),
                LocalDateTime.now()
        );

        GuildEntity entity = guildMapper.toEntity(guild);
        guildRepository.persist(entity);

        return guildMapper.toDTO(guild);
    }

    public List<GuildDTO> findAll() {
        return findAll(0, 20, null);
    }

    public List<GuildDTO> findAll(
            int page, int size, String name
    ) {
        validatePagination(page, size);

        return guildRepository.findPage(page, size, name)
                .stream()
                .map(guildMapper::toDomain)
                .map(guildMapper::toDTO)
                .toList();
    }

    private void validatePagination(int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new BadRequestException(
                    "Page must be >= 0 and size between 1 and 100"
            );
        }
    }

    public GuildDTO findByDiscordId(String discordId) {
        GuildEntity entity = guildRepository
                .findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Guild not found: " + discordId
                        )
                );

        return guildMapper.toDTO(guildMapper.toDomain(entity));
    }

    @Transactional
    public GuildDTO update(
            String discordId,
            UpdateGuildRequest request
    ) {
        GuildEntity entity = guildRepository
                .findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Guild not found: " + discordId
                        )
                );

        entity.setName(request.name());
        entity.setDescription(request.description());

        return guildMapper.toDTO(guildMapper.toDomain(entity));
    }

    @Transactional
    public void delete(String discordId) {
        GuildEntity entity = guildRepository
                .findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Guild not found: " + discordId
                        )
                );

        guildRepository.delete(entity);
    }
}
