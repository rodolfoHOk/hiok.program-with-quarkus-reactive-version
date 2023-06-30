package dev.hiok.application.mapper;

import dev.hiok.application.dto.FruitInputDTO;
import dev.hiok.domain.entity.FruitEntity;

public class FruitMapper {

  public static FruitEntity toEntity(FruitInputDTO inputDTO) {
    var entity = new FruitEntity();
    entity.name = inputDTO.name();
    entity.quantity = inputDTO.quantity().intValue();
    return entity;
  }

}
