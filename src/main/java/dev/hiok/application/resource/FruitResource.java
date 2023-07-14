package dev.hiok.application.resource;

import dev.hiok.application.dto.FruitCountDTO;
import dev.hiok.application.dto.FruitInputDTO;
import dev.hiok.application.dto.FruitOutputDTO;
import dev.hiok.application.dto.FruitQuantityDTO;
import dev.hiok.application.dto.Paged;
import dev.hiok.application.mapper.FruitMapper;
import dev.hiok.domain.service.FruitService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/fruits")
@ApplicationScoped
@Tag(name = "Fruits")
@SecuritySchemes(value = {
  @SecurityScheme(securitySchemeName = "BearerToken", type = SecuritySchemeType.HTTP, scheme = "Bearer")
})
public class FruitResource {

  @Inject
  FruitService fruitService;

  @GET
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Page and sort the fruits list")
  public Uni<Paged<FruitOutputDTO>> pagedListBy(
    @DefaultValue("id") @QueryParam("sort_field") String sortField,
    @DefaultValue("Ascending") @QueryParam("sort_direction") String sortDirection,
    @DefaultValue("10") @QueryParam("page_size") int pageSize,
    @DefaultValue("0") @QueryParam("page_number") int pageNumber)
  {
    Sort sortTest = Sort.by(sortField).direction(Sort.Direction.valueOf(sortDirection));
    Page pageTest = Page.of(pageNumber, pageSize);

    return fruitService.pagedSortedList(sortTest, pageTest)
      .map(list -> new Paged<FruitOutputDTO>(
        list.stream().map(FruitMapper::toRepresentationModel).toList(),
        pageTest.size,
        pageTest.index)
      );
  }

  @GET
  @Path("/count")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Total fruits count")
  public Uni<FruitCountDTO> totalCount() {
    return fruitService.count().map(FruitCountDTO::new);
  }

  @GET
  @Path("/{id}")
  @RolesAllowed(value = "user")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Search a fruit by ID")
  @APIResponse(responseCode = "200", description = "OK",  content = @Content(schema = @Schema(implementation = FruitOutputDTO.class)))
  @APIResponse(responseCode = "401", description = "UNAUTHORIZED")
  @APIResponse(responseCode = "404", description = "NOT FOUND")
  @SecurityRequirement(name = "BearerToken")
  public Uni<RestResponse<FruitOutputDTO>> findById(
    @PathParam("id") @Parameter(description = "Fruit id", example = "1", required = true) Long id
  ) {
    return fruitService.findById(id)
      .map(foundedFruit ->
        foundedFruit == null ? RestResponse.status(RestResponse.Status.NOT_FOUND)
          : RestResponse.ok(FruitMapper.toRepresentationModel(foundedFruit)));
  }

  @POST
  @RolesAllowed(value = "admin")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Register a new fruit")
  @APIResponse(responseCode = "201", description = "CREATED",  content = @Content(schema = @Schema(implementation = FruitOutputDTO.class)))
  @APIResponse(responseCode = "400", description = "BAD REQUEST")
  @APIResponse(responseCode = "401", description = "UNAUTHORIZED")
  @APIResponse(responseCode = "403", description = "FORBIDDEN")
  @SecurityRequirement(name = "BearerToken")
  public Uni<RestResponse<FruitOutputDTO>> create(@Valid FruitInputDTO fruitInputDTO) {
    var fruitEntity = FruitMapper.toDomainEntity(fruitInputDTO);
    return fruitService.create(fruitEntity)
      .map(savedFruit -> RestResponse.status(RestResponse.Status.CREATED,
        FruitMapper.toRepresentationModel(savedFruit)));
  }

  @PUT
  @Path("/{id}")
  @RolesAllowed(value = "admin")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update fruit by ID")
  @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FruitOutputDTO.class)))
  @APIResponse(responseCode = "400", description = "BAD REQUEST")
  @APIResponse(responseCode = "401", description = "UNAUTHORIZED")
  @APIResponse(responseCode = "403", description = "FORBIDDEN")
  @SecurityRequirement(name = "BearerToken")
  public Uni<RestResponse<FruitOutputDTO>> update(
    @PathParam("id") @Parameter(description = "Fruit id", example = "1", required = true) Long id,
    @Valid FruitInputDTO fruitInputDTO
  ) {
    return fruitService.update(id, FruitMapper.toDomainEntity(fruitInputDTO))
      .map(fruit -> fruit == null ? RestResponse.status(RestResponse.Status.BAD_REQUEST)
        : RestResponse.ok(FruitMapper.toRepresentationModel(fruit)));
  }

  @PATCH
  @Path("/{name}")
  @RolesAllowed(value = "user")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update fruit quantity by name")
  @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FruitOutputDTO.class)))
  @APIResponse(responseCode = "400", description = "BAD REQUEST")
  @APIResponse(responseCode = "401", description = "UNAUTHORIZED")
  @SecurityRequirement(name = "BearerToken")
  public Uni<RestResponse<FruitOutputDTO>> updateQuantity(
    @PathParam("name") @Parameter(description = "Fruit name", example = "Maça", required = true) String name,
    @Valid FruitQuantityDTO fruitQuantityDTO
  ) {
    return fruitService.updateQuantity(name, fruitQuantityDTO.quantity().intValue())
      .map(fruit -> fruit == null ? RestResponse.status(RestResponse.Status.BAD_REQUEST)
        : RestResponse.ok(FruitMapper.toRepresentationModel(fruit)));
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed(value = "admin")
  @Operation(summary = "Delete fruit by ID")
  @APIResponse(responseCode = "204", description = "NO CONTENT")
  @APIResponse(responseCode = "400", description = "BAD REQUEST")
  @APIResponse(responseCode = "401", description = "UNAUTHORIZED")
  @APIResponse(responseCode = "403", description = "FORBIDDEN")
  @SecurityRequirement(name = "BearerToken")
  public Uni<RestResponse<Void>> delete(
    @PathParam("id") @Parameter(description = "Fruit id", example = "1", required = true) Long id
  ) {
    return fruitService.delete(id)
      .map(deleted -> deleted ? RestResponse.status(Response.Status.NO_CONTENT)
        : RestResponse.status(Response.Status.BAD_REQUEST));
  }

}


