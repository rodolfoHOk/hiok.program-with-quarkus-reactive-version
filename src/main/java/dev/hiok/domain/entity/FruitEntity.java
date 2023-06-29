package dev.hiok.domain.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class FruitEntity extends PanacheEntityBase {

  @Id
  @SequenceGenerator(
    name = "fruitSequenceGenerator",
    sequenceName = "fruit_sequence",
    allocationSize = 1,
    initialValue = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruitSequenceGenerator")
  public Long id;

  @Column(nullable = false, unique = true)
  public String name;

  @Column(nullable = false)
  public Integer quantity;

}
