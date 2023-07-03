package dev.hiok.domain.service;

import dev.hiok.domain.entity.FruitEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FruitServiceTest {

  @Inject
  FruitService fruitService;

  @Test
  @Order(1)
  @RunOnVertxContext
  public void testFruitsList(UniAsserter asserter) {
    asserter.assertThat(
      () -> fruitService.list(),
      result -> Assertions.assertTrue(result.size() >= 3));
  }

  @Test
  @Order(2)
  @RunOnVertxContext
  public void testCountFruits(UniAsserter asserter) {
    asserter.assertThat(
      () -> fruitService.count(),
      result -> Assertions.assertTrue(result >= 3));
  }

  @Test
  @Order(3)
  @RunOnVertxContext
  public void testPagedAndSortedFruitsList(UniAsserter asserter) {
    Sort sort = Sort.by("id").ascending();
    Page page = Page.of(0, 2);
    asserter.assertThat(
      () -> fruitService.pagedSortedList(sort, page),
      result -> {
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).id);
      });
  }

  @Test
  @Order(4)
  @RunOnVertxContext
  public void testPagedAndSortedFruitsList2(UniAsserter asserter) {
    Sort sort = Sort.by("name").descending();
    Page page = Page.of(0, 3);
    asserter.assertThat(
      () -> fruitService.pagedSortedList(sort, page),
      result -> {
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Pera", result.get(0).name);
      });
  }

  @Test
  @Order(5)
  @RunOnVertxContext
  public void testGetFruitByIdWithValidId(UniAsserter asserter) {
    asserter.assertThat(
      () -> fruitService.findById(1L),
      result -> {
        Assertions.assertEquals(result.id, 1);
        Assertions.assertEquals(result.name, "Maçã");
        Assertions.assertEquals(result.quantity, 5);
      }
    );
  }

  @Test
  @Order(6)
  @RunOnVertxContext
  public void testGetFruitByIdWithInvalidId(UniAsserter asserter) {
    asserter.assertThat(
      () -> fruitService.findById(5L),
      Assertions::assertNull
    );
  }

  @Test
  @Order(7)
  @RunOnVertxContext
  public void testCreateFruit(UniAsserter asserter) {
    var newFruit = new FruitEntity();
    newFruit.name = "Abacaxi";
    newFruit.quantity = 2;

    asserter.assertThat(
      () -> fruitService.create(newFruit),
      result -> {
        Assertions.assertNotNull(result.id);
        Assertions.assertEquals(result.name, newFruit.name);
        Assertions.assertEquals(result.quantity, newFruit.quantity);
      }
    );
  }

}
