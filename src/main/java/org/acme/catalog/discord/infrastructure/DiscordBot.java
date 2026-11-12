package org.acme.catalog.discord.infrastructure;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import org.acme.catalog.user.application.UserService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import reactor.core.publisher.Mono;

@ApplicationScoped
public class DiscordBot {

    @ConfigProperty(name = "discord.token")
    String token;

    private final UserService userService;
    private GatewayDiscordClient gateway;

    public DiscordBot(UserService userService) {
        this.userService = userService;
    }

    void onStart(@Observes StartupEvent event) {

        gateway = DiscordClientBuilder.create(token)
                .build()
                .gateway()
                .setEnabledIntents(IntentSet.of(
                        Intent.GUILDS,
                        Intent.GUILD_MEMBERS,
                        Intent.GUILD_MESSAGES,
                        Intent.MESSAGE_CONTENT
                ))
                .login()
                .block();

        if (gateway == null) {
            throw new IllegalStateException(
                    "Connexion Discord impossible"
            );
        }

        // Commande !ping
        gateway.on(MessageCreateEvent.class)
                .filter(message -> !message.getMessage()
                        .getAuthor()
                        .map(user -> user.isBot())
                        .orElse(false))
                .filter(message -> message.getMessage()
                        .getContent()
                        .equalsIgnoreCase("!ping"))
                .flatMap(message -> message.getMessage()
                        .getChannel()
                        .flatMap(channel ->
                                channel.createMessage("Pong!")))
                .subscribe();

        // Arrivée d'un nouveau membre
        gateway.on(MemberJoinEvent.class)
                .flatMap(joinEvent -> {

                    var member = joinEvent.getMember();

                    String discordId =
                            member.getId().asString();

                    String username =
                            member.getUsername();

                    String displayName =
                            member.getDisplayName();

                    Snowflake roleId =
                            Snowflake.of("1566923357985706054");

                    return Mono.fromRunnable(() ->
                                    userService.registerDiscordMember(
                                            discordId,
                                            username,
                                            displayName
                                    ))
                            .subscribeOn(
                                    reactor.core.scheduler.Schedulers.boundedElastic()
                            )
                            .then(member.addRole(roleId))
                            .doOnSuccess(ignored ->
                                    System.out.println(
                                            "Membre enregistré et rôle attribué : "
                                                    + username
                                    ))
                            .onErrorResume(error -> {
                                System.err.println(
                                        "Erreur pour " + username
                                                + " : " + error.getMessage()
                                );
                                return Mono.empty();
                            });
                })
                .subscribe();

        System.out.println("Bot Manager connecté à Discord !");
    }

    void onStop(@Observes ShutdownEvent event) {
        if (gateway != null) {
            gateway.logout().block();
        }
    }
}