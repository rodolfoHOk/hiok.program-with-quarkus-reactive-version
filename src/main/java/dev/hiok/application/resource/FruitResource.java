package dev.hiok.application.resource;

import dev.hiok.application.dto.FruitInputDTO;
import dev.hiok.application.dto.FruitOutputDTO;
import dev.hiok.application.mapper.FruitMapper;
import dev.hiok.domain.service.FruitService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/fruits")
@ApplicationScoped
public class FruitResource {

  @Inject
  FruitService fruitService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<List<FruitOutputDTO>> list() {
    return fruitService.list()
      .map(fruitEntities -> fruitEntities.stream().map(FruitMapper::toRepresentationModel).toList());
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<RestResponse<FruitOutputDTO>> create(@Valid FruitInputDTO fruitInputDTO) {
    var fruitEntity = FruitMapper.toDomainEntity(fruitInputDTO);
    return fruitService.create(fruitEntity)
      .map(savedFruit -> RestResponse.status(RestResponse.Status.CREATED,
        FruitMapper.toRepresentationModel(savedFruit)));
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<RestResponse<FruitOutputDTO>> findById(@PathParam("id") Long id) {
    return fruitService.findById(id)
      .map(foundedFruit ->
        foundedFruit == null ? RestResponse.status(RestResponse.Status.NOT_FOUND)
          : RestResponse.ok(FruitMapper.toRepresentationModel(foundedFruit)));
  }

}
