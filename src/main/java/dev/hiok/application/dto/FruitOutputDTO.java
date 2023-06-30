package dev.hiok.application.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record FruitOutputDTO(
  @Schema(example = "1")
  Long id,
  @Schema(example = "Maçã")
  String name,
  @Schema(example = "5")
  Integer quantity
) {

}
