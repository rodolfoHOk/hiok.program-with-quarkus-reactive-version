package dev.hiok.domain.service;

import dev.hiok.core.utils.TransactionIdentifier;
import dev.hiok.domain.entity.FruitEntity;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@WithSession
@ApplicationScoped
public class FruitService {

  @Inject
  TransactionIdentifier transactionIdentifier;

  public Uni<List<FruitEntity>> list() {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    return FruitEntity.listAll();
  }

  public Uni<List<FruitEntity>> pagedSortedList(Sort sort, Page page) {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    return FruitEntity.findAll(sort).page(page).list();
  }

  public Uni<Long> count() {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    return FruitEntity.count();
  }

  public Uni<FruitEntity> findById(Long id) {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    return FruitEntity.findById(id);
  }

  public Uni<FruitEntity> create(FruitEntity fruit) {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    var newFruit = new FruitEntity();
    newFruit.name = fruit.name;
    newFruit.quantity = fruit.quantity;
    return Panache.withTransaction(newFruit::persist);
  }

  public Uni<FruitEntity> update(Long id, FruitEntity fruit) {
    return Panache.withTransaction(() -> findById(id)
      .onItem().ifNotNull().transform(entity -> {
        entity.name = fruit.name;
        entity.quantity = fruit.quantity;
        return entity;
      })
      .onFailure().recoverWithNull()
    );
  }

  public Uni<FruitEntity> findByName(String name) {
    System.out.println(transactionIdentifier.getTransactionIdentifier());
    return FruitEntity.find("name", name).firstResult();
  }

  public Uni<FruitEntity> updateQuantity(String name, int quantity) {
    return Panache.withTransaction(() -> findByName(name)
      .onItem().ifNotNull().transform(entity -> {
        entity.quantity = quantity;
        return entity;
      })
      .onFailure().recoverWithNull()
    );
  }

}
