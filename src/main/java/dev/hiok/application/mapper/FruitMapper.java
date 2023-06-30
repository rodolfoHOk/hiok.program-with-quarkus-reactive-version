package dev.hiok.application.mapper;

import dev.hiok.application.dto.FruitInputDTO;
import dev.hiok.application.dto.FruitOutputDTO;
import dev.hiok.domain.entity.FruitEntity;

public final class FruitMapper {

  private FruitMapper() {
  }

  public static FruitEntity toDomainEntity(FruitInputDTO inputDTO) {
    var entity = new FruitEntity();
    entity.name = inputDTO.name();
    entity.quantity = inputDTO.quantity().intValue();
    return entity;
  }

  public static FruitOutputDTO toRepresentationModel(FruitEntity entity) {
    return new FruitOutputDTO(entity.id, entity.name, entity.quantity);
  }

}
