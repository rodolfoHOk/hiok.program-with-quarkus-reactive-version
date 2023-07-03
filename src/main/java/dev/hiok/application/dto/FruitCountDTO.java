package dev.hiok.application.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record FruitCountDTO(
  @Schema(example = "3") Long count
) {

}
