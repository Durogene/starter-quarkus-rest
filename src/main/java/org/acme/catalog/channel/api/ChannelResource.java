
package org.acme.catalog.channel.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.catalog.channel.application.ChannelService;
import org.acme.catalog.channel.application.dto.ChannelDTO;
import org.acme.catalog.channel.application.dto.usecases.CreateChannelRequest;
import org.acme.catalog.channel.application.dto.usecases.UpdateChannelRequest;

import java.net.URI;
import java.util.List;

@Path("/api/v1/channels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChannelResource {

    @Inject
    ChannelService service;

    @GET
    public List<ChannelDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("name") String name
    ) {
        return service.findAll(page, size, name);
    }

    @GET
    @Path("/{discordId}")
    public ChannelDTO getByDiscordId(
            @PathParam("discordId") String discordId
    ) {
        return service.findByDiscordId(discordId);
    }

    @POST
    public Response create(@Valid CreateChannelRequest request) {
        ChannelDTO channel = service.create(request);

        return Response.created(
                URI.create("/api/v1/channels/" + channel.discordId())
        ).entity(channel).build();
    }

    @PUT
    @Path("/{discordId}")
    public ChannelDTO update(
            @PathParam("discordId") String discordId,
            @Valid UpdateChannelRequest request
    ) {
        return service.update(discordId, request);
    }

    @DELETE
    @Path("/{discordId}")
    public Response delete(
            @PathParam("discordId") String discordId
    ) {
        service.delete(discordId);
        return Response.noContent().build();
    }
}
