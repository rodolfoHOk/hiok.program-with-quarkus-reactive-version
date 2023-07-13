package dev.hiok.application.dto;

import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Set;

public class UserDTO {

  @Schema(example = "alice")
  private final String username;

  @Schema(example = "[ \"user\" ]")
  private final Set<String> roles;

  public UserDTO(SecurityIdentity securityIdentity) {
    this.username = securityIdentity.getPrincipal().getName();
    this.roles = securityIdentity.getRoles();
  }

  public String getUsername() {
    return this.username;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

}
