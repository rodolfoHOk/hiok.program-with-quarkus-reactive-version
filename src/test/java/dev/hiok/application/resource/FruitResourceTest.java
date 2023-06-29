package dev.hiok.application.resource;

import dev.hiok.domain.entity.FruitEntity;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestHTTPEndpoint(FruitResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FruitResourceTest {

  @Test
  @Order(1)
  public void testFruitsList() {
    RestAssured
      .given()
      .when().get()
      .then()
        .statusCode(200)
        .body("size()", CoreMatchers.is(3));
  }

  @Test
  @Order(2)
  public void testGetFruitByIdOk() {
    RestAssured
      .given()
      .when().get("/1")
      .then()
        .statusCode(200)
        .body("id", CoreMatchers.is(1))
        .body("name", CoreMatchers.is("Maçã"))
        .body("quantity", CoreMatchers.is(5));
  }

  @Test
  @Order(2)
  public void testGetFruitByIdNotFound() {
    RestAssured
      .given()
      .when().get("/5")
      .then().statusCode(404);
  }

  @Test
  @Order(4)
  public void testCreateFruit() {
    var newFruit = new FruitEntity();
    newFruit.name = "Melão";
    newFruit.quantity = 1;

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(newFruit)
      .when().post()
      .then()
        .statusCode(201)
        .body("id", CoreMatchers.is(CoreMatchers.notNullValue()))
        .body("name", CoreMatchers.is(newFruit.name))
        .body("quantity", CoreMatchers.is(newFruit.quantity));
  }

}
