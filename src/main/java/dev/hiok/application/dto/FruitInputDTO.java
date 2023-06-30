package dev.hiok.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record FruitInputDTO(
  @NotBlank(message = "Name cannot be blank")
  @Schema(example = "Abacate", required = true)
  String name,
  @NotNull(message = "Quantity cannot be null")
  @Positive(message = "Quantity must be greater than zero")
  @Digits(integer = 9, fraction = 0, message = "Quantity must be an integer")
  @Schema(example = "2", required = true)
  BigDecimal quantity
) {

}
