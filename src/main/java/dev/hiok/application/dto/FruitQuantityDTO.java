package dev.hiok.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record FruitQuantityDTO(
  @Schema(example = "2", required = true)
  @NotNull(message = "Quantity cannot be null")
  @PositiveOrZero(message = "Quantity must be greater than or igual zero")
  @Digits(integer = 9, fraction = 0, message = "Quantity must be an integer")
  BigDecimal quantity
) {

}
