
package org.acme.catalog.role.infrastructure.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.catalog.role.application.dto.RoleDTO;
import org.acme.catalog.role.domain.Role;
import org.acme.catalog.role.infrastructure.persistence.RoleEntity;

@ApplicationScoped
public class RoleMapper {

    public RoleEntity toEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setDiscordId(role.discordId());
        entity.setGuildDiscordId(role.guildDiscordId());
        entity.setName(role.name());
        entity.setCreatedAt(role.createdAt());
        return entity;
    }

    public Role toDomain(RoleEntity entity) {
        return Role.of(
                entity.getDiscordId(),
                entity.getGuildDiscordId(),
                entity.getName(),
                entity.getCreatedAt()
        );
    }

    public RoleDTO toDTO(Role role) {
        return new RoleDTO(
                role.discordId(),
                role.guildDiscordId(),
                role.name(),
                role.createdAt()
        );
    }
}
