package test.eu;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;
import io.smallrye.mutiny.Uni;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import test.eu.entity.RoleEntity;
import test.eu.model.Page;
import test.eu.model.Role;


/***
 * Resource for user queries and operations.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class Users  {

    private static final Logger log = Logger.getLogger(Users.class);

    @Inject
    Mutiny.SessionFactory sf;


    /***
     * Page of roles
     */
    public static class PageOfRoles extends Page<Role, Long> {
        public PageOfRoles(String baseUri, long from, int limit, List<RoleEntity> roles_) {
            super();

            var roles = roles_.stream().map(Role::new).collect(Collectors.toList());
            populate(baseUri, from, limit, roles, false);
        }
    }


    /***
     * Constructor
     */
    public Users() {}

    /**
     * List defined roles.
     * @return API Response, wraps an ActionSuccess({@link PageOfRoles}) or an ActionError entity
     */
    @GET
    @Path("/role/definitions")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PageOfRoles.class))),
    })
    public Uni<Response> listRoles()
    {
        log.info("Listing role definitions");

        Uni<Response> result = Uni.createFrom().nullItem()

            .chain(unused -> {
                return sf.withSession(session -> RoleEntity.getAllRoles(session));
            })
            .chain(roles -> {
                // Got roles, success
                log.info("Got role definitions");
                var page = new PageOfRoles("#", 0, 100, roles);
                return Uni.createFrom().item(Response.ok(page).build());
            })
            .onFailure().recoverWithItem(e -> {
                log.error("Failed to list role definitions");
                return Response.noContent().build();
            });

        return result;
    }
}
