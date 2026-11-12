
package org.acme.catalog.guild.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.catalog.guild.application.GuildService;
import org.acme.catalog.guild.application.dto.GuildDTO;
import org.acme.catalog.guild.application.dto.usecases.CreateGuildRequest;
import org.acme.catalog.guild.application.dto.usecases.UpdateGuildRequest;

import java.net.URI;
import java.util.List;

@Path("/api/v1/guilds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuildResource {

    @Inject
    GuildService guildService;

    @GET
    public List<GuildDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("name") String name
    ) {
        return guildService.findAll(page, size, name);
    }

    @GET
    @Path("/{discordId}")
    public GuildDTO getByDiscordId(
            @PathParam("discordId") String discordId
    ) {
        return guildService.findByDiscordId(discordId);
    }

    @POST
    public Response create(@Valid CreateGuildRequest request) {
        GuildDTO guild = guildService.create(request);

        return Response.created(
                URI.create("/api/v1/guilds/" + guild.discordId())
        ).entity(guild).build();
    }

    @PUT
    @Path("/{discordId}")
    public GuildDTO update(
            @PathParam("discordId") String discordId,
            @Valid UpdateGuildRequest request
    ) {
        return guildService.update(discordId, request);
    }

    @DELETE
    @Path("/{discordId}")
    public Response delete(
            @PathParam("discordId") String discordId
    ) {
        guildService.delete(discordId);
        return Response.noContent().build();
    }
}
