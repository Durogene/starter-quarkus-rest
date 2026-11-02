package org.acme.catalog.user.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {


    public Optional<UserEntity> findByDiscordId(String discordId) {
        return find("discordId", discordId).firstResultOptional();
    }


    public boolean existsByDiscordId(String discordId) {
        return count("discordId", discordId) > 0;
    }
}