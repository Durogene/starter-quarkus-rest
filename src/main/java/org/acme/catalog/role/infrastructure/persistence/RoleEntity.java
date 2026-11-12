
package org.acme.catalog.role.infrastructure.persistence;

import jakarta.persistence.*;
import org.acme.catalog.guild.infrastructure.persistence.GuildEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "discord_roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discord_id", nullable = false, unique = true)
    private String discordId;

    @Column(name = "guild_discord_id", nullable = false)
    private String guildDiscordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "guild_discord_id",
            referencedColumnName = "discord_id",
            insertable = false,
            updatable = false
    )
    private GuildEntity guild;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getGuildDiscordId() {
        return guildDiscordId;
    }

    public void setGuildDiscordId(String guildDiscordId) {
        this.guildDiscordId = guildDiscordId;
    }

    public GuildEntity getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
