
package org.acme.catalog.channel.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ApplicationScoped
public class ChannelRepository
        implements PanacheRepository<ChannelEntity> {

    public Optional<ChannelEntity> findByDiscordId(String discordId) {
        return find("discordId", discordId).firstResultOptional();
    }

    public boolean existsByDiscordId(String discordId) {
        return count("discordId", discordId) > 0;
    }

    public List<ChannelEntity> findPage(
            int page, int size, String name
    ) {
        if (name == null || name.isBlank()) {
            return find("order by name, discordId")
                    .page(Page.of(page, size))
                    .list();
        }

        return find(
                "lower(name) like ?1 order by name, discordId",
                "%" + name.toLowerCase(Locale.ROOT).trim() + "%"
        ).page(Page.of(page, size)).list();
    }
}
