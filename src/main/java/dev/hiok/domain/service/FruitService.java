package dev.hiok.domain.service;

import dev.hiok.domain.entity.FruitEntity;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@WithSession
@ApplicationScoped
public class FruitService {

  public Uni<List<FruitEntity>> list() {
    return FruitEntity.listAll();
  }

  public Uni<FruitEntity> findById(Long id) {
    return FruitEntity.findById(id);
  }

  public Uni<FruitEntity> create(FruitEntity fruit) {
    var newFruit = new FruitEntity();
    newFruit.name = fruit.name;
    newFruit.quantity = fruit.quantity;
    return Panache.withTransaction(newFruit::persist);
  }

}
