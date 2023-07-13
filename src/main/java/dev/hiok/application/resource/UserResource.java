package dev.hiok.application.resource;

import dev.hiok.application.dto.UserDTO;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.NoCache;


@Path("/api/users")
public class UserResource {

  @Inject
  SecurityIdentity securityIdentity;

  @GET
  @Path("/me")
  @NoCache
  public UserDTO userInfo() {
    return new UserDTO(securityIdentity);
  }

}
