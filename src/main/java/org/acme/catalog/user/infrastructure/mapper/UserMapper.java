package org.acme.catalog.user.infrastructure.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.catalog.user.application.dto.UserDTO;
import org.acme.catalog.user.domain.User;
import org.acme.catalog.user.infrastructure.persistence.UserEntity;

@ApplicationScoped
public class UserMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();

        entity.setDiscordId(user.discordId());
        entity.setUsername(user.username());
        entity.setDisplayName(user.displayName());
        entity.setJoinedAt(user.joinedAt());

        return entity;
    }

    public User toDomain(UserEntity entity) {
        return User.of(
                entity.getDiscordId(),
                entity.getUsername(),
                entity.getDisplayName(),
                entity.getJoinedAt()
        );
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.discordId(),
                user.username(),
                user.displayName(),
                user.joinedAt()
        );
    }
}