
package org.acme.catalog.role.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import org.acme.catalog.guild.infrastructure.persistence.GuildRepository;
import org.acme.catalog.role.application.dto.RoleDTO;
import org.acme.catalog.role.application.dto.usecases.CreateRoleRequest;
import org.acme.catalog.role.application.dto.usecases.UpdateRoleRequest;
import org.acme.catalog.role.domain.Role;
import org.acme.catalog.role.infrastructure.mapper.RoleMapper;
import org.acme.catalog.role.infrastructure.persistence.RoleEntity;
import org.acme.catalog.role.infrastructure.persistence.RoleRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;
    private final GuildRepository guildRepository;

    public RoleService(
            RoleRepository repository,
            RoleMapper mapper,
            GuildRepository guildRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guildRepository = guildRepository;
    }

    @Transactional
    public RoleDTO create(CreateRoleRequest request) {
        if (repository.existsByDiscordId(request.discordId())) {
            throw new WebApplicationException(
                    "Role already exists", 409
            );
        }

        if (!guildRepository.existsByDiscordId(request.guildDiscordId())) {
            throw new NotFoundException("Guild not found");
        }

        Role role = Role.of(
                request.discordId(),
                request.guildDiscordId(),
                request.name(),
                LocalDateTime.now()
        );

        repository.persist(mapper.toEntity(role));
        return mapper.toDTO(role);
    }

    public List<RoleDTO> findAll() {
        return findAll(0, 20, null);
    }

    public List<RoleDTO> findAll(
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

    public RoleDTO findByDiscordId(String discordId) {
        RoleEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Role not found")
                );

        return mapper.toDTO(mapper.toDomain(entity));
    }

    @Transactional
    public RoleDTO update(
            String discordId,
            UpdateRoleRequest request
    ) {
        RoleEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Role not found")
                );

        entity.setName(request.name());
        return mapper.toDTO(mapper.toDomain(entity));
    }

    @Transactional
    public void delete(String discordId) {
        RoleEntity entity = repository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException("Role not found")
                );

        repository.delete(entity);
    }
}
