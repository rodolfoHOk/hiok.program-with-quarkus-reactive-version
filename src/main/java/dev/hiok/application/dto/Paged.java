package dev.hiok.application.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

public record Paged<T>(
  List<T> content,
  @Schema(example = "10")
  int pageSize,
  @Schema(example = "1")
  int pageNumber
) {

}
