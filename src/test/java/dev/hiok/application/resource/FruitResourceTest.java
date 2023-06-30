package dev.hiok.application.resource;

import dev.hiok.application.dto.FruitInputDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;

@QuarkusTest
@TestHTTPEndpoint(FruitResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FruitResourceTest {

  @Test
  @Order(1)
  public void shouldReturnOk_WhenFruitsList() {
    RestAssured
      .given()
      .when().get()
      .then()
        .statusCode(200)
        .body("size()", CoreMatchers.is(3));
  }

  @Test
  @Order(2)
  public void shouldReturnOk_WhenGetFruitWithAValidId() {
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
  public void shouldReturnNotFound_WhenGetFruitWithAInvalidId() {
    RestAssured
      .given()
      .when().get("/5")
      .then().statusCode(404);
  }

  @Test
  @Order(4)
  public void shouldReturnBaqRequest_WhenCreateFruitWithoutName() {
    var fruitInputDTO = new FruitInputDTO(null, BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().post()
      .then()
        .statusCode(400)
        .body(CoreMatchers.containsString("Name cannot be blank"));
  }

  @Test
  @Order(5)
  public void shouldReturnBaqRequest_WhenCreateFruitWithABlankName() {
    var fruitInputDTO = new FruitInputDTO("", BigDecimal.valueOf(1));

    RestAssured
      .given()
      .contentType(ContentType.JSON)
      .body(fruitInputDTO)
      .when().post()
      .then()
      .statusCode(400)
      .body(CoreMatchers.containsString("Name cannot be blank"));
  }

  @Test
  @Order(6)
  public void shouldReturnBaqRequest_WhenCreateFruitWithoutQuantity() {
    var fruitInputDTO = new FruitInputDTO("Melão", null);

    RestAssured
      .given()
      .contentType(ContentType.JSON)
      .body(fruitInputDTO)
      .when().post()
      .then()
      .statusCode(400)
      .body(CoreMatchers.containsString("Quantity cannot be null"));
  }

  @Test
  @Order(7)
  public void shouldReturnBaqRequest_WhenCreateFruitWithANegativeQuantity() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.valueOf(-1));

    RestAssured
      .given()
      .contentType(ContentType.JSON)
      .body(fruitInputDTO)
      .when().post()
      .then()
      .statusCode(400)
      .body(CoreMatchers.containsString("Quantity must be greater than zero"));
  }

  @Test
  @Order(7)
  public void shouldReturnBaqRequest_WhenCreateFruitWithAZeroQuantity() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.ZERO);

    RestAssured
      .given()
      .contentType(ContentType.JSON)
      .body(fruitInputDTO)
      .when().post()
      .then()
      .statusCode(400)
      .body(CoreMatchers.containsString("Quantity must be greater than zero"));
  }

  @Test
  @Order(7)
  public void shouldReturnBaqRequest_WhenCreateFruitWithAFractionQuantity() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.valueOf(2.5));

    RestAssured
      .given()
      .contentType(ContentType.JSON)
      .body(fruitInputDTO)
      .when().post()
      .then()
      .statusCode(400)
      .body(CoreMatchers.containsString("Quantity must be an integer"));
  }

  @Test
  @Order(10)
  public void shouldCreated_WhenCreateFruitWithAValidInput() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().post()
      .then()
        .statusCode(201)
        .body("id", CoreMatchers.is(CoreMatchers.notNullValue()))
        .body("name", CoreMatchers.is(fruitInputDTO.name()))
        .body("quantity", CoreMatchers.is(fruitInputDTO.quantity().intValue()));
  }

}
