package org.acme.catalog.user.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.catalog.user.application.UserService;
import org.acme.catalog.user.application.dto.UserDTO;
import org.acme.catalog.user.application.dto.usecases.CreateUserRequest;

import java.net.URI;
import java.util.List;
import org.acme.catalog.user.application.dto.usecases.UpdateUserRequest;
@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    public List<UserDTO> getAll() {
        return userService.findAll();
    }

    @POST
    public Response create(@Valid CreateUserRequest request) {

        UserDTO createdUser = userService.create(request);

        return Response
                .created(URI.create(
                        "/api/v1/users/" + createdUser.discordId()
                ))
                .entity(createdUser)
                .build();
    }

    @GET
    @Path("/{discordId}")
    public UserDTO getByDiscordId(
            @PathParam("discordId") String discordId
    ) {
        return userService.findByDiscordId(discordId);
    }

    @PUT
    @Path("/{discordId}")
    public UserDTO update(
            @PathParam("discordId") String discordId,
            @Valid UpdateUserRequest request
    ) {
        return userService.update(discordId, request);
    }

    @DELETE
    @Path("/{discordId}")
    public Response delete(
            @PathParam("discordId") String discordId
    ) {
        userService.delete(discordId);

        return Response.noContent().build();
    }
}