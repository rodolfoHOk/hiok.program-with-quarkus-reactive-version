package dev.hiok.application.resource;

import dev.hiok.application.dto.UserDTO;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.NoCache;


@Path("/api/users")
@ApplicationScoped
@Tag(name = "Users")
@SecuritySchemes(value = {
  @SecurityScheme(securitySchemeName = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "Bearer")
})
public class UserResource {

  @Inject
  SecurityIdentity securityIdentity;

  @GET
  @Path("/me")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  @Operation(summary = "Get user information")
  @SecurityRequirement(name = "bearerToken")
  @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserDTO.class)))
  @APIResponse(responseCode = "401", description = "Unauthorized")
  public UserDTO userInfo() {
    return new UserDTO(securityIdentity);
  }

}
