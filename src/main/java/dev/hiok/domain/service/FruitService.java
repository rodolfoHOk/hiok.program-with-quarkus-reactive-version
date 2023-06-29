package dev.hiok.domain.service;

import dev.hiok.domain.entity.FruitEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FruitService {

  public Uni<List<FruitEntity>> list() {
    return FruitEntity.listAll();
  }

  public Uni<FruitEntity> findById(Long id) {
    return FruitEntity.findById(id);
  }

  @Transactional
  public Uni<FruitEntity> create(FruitEntity fruit) {
    var newFruit = new FruitEntity();
    newFruit.name = fruit.name;
    newFruit.quantity = fruit.quantity;
    return newFruit.persist();
  }

}
