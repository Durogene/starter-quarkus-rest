
package org.acme.catalog.role.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.catalog.role.application.RoleService;
import org.acme.catalog.role.application.dto.RoleDTO;
import org.acme.catalog.role.application.dto.usecases.CreateRoleRequest;
import org.acme.catalog.role.application.dto.usecases.UpdateRoleRequest;

import java.net.URI;
import java.util.List;

@Path("/api/v1/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {

    @Inject
    RoleService service;

    @GET
    public List<RoleDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("name") String name
    ) {
        return service.findAll(page, size, name);
    }

    @GET
    @Path("/{discordId}")
    public RoleDTO getByDiscordId(
            @PathParam("discordId") String discordId
    ) {
        return service.findByDiscordId(discordId);
    }

    @POST
    public Response create(@Valid CreateRoleRequest request) {
        RoleDTO role = service.create(request);

        return Response.created(
                URI.create("/api/v1/roles/" + role.discordId())
        ).entity(role).build();
    }

    @PUT
    @Path("/{discordId}")
    public RoleDTO update(
            @PathParam("discordId") String discordId,
            @Valid UpdateRoleRequest request
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
