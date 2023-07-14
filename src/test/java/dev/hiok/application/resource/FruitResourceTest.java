package dev.hiok.application.resource;

import dev.hiok.application.dto.FruitInputDTO;
import dev.hiok.application.dto.FruitQuantityDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
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
  public void shouldReturnOk_WhenFruitsListWithPageSize2PageNumber0() {
    RestAssured
      .given()
        .queryParam("sort_field", "id")
        .queryParam("sort_direction", "Ascending")
        .queryParam("page_size", 2)
        .queryParam("page_number", 0)
      .when().get()
      .then()
        .statusCode(200)
        .body("content.size()", CoreMatchers.is(2))
        .body("pageSize", CoreMatchers.is(2))
        .body("pageNumber", CoreMatchers.is(0));
  }

  @Test
  @Order(3)
  public void shouldReturnOk_WhenFruitsListWithPageSize2PageNumber1() {
    RestAssured
      .given()
        .queryParam("sort_field", "id")
        .queryParam("sort_direction", "Ascending")
        .queryParam("page_size", 2)
        .queryParam("page_number", 1)
      .when().get()
      .then()
        .statusCode(200)
        .body("content.size()", CoreMatchers.is(1))
        .body("pageSize", CoreMatchers.is(2))
        .body("pageNumber", CoreMatchers.is(1));
  }

  @Test
  @Order(4)
  public void shouldReturnOk_WhenFruitsListWithSortFieldNameAscending() {
    RestAssured
      .given()
        .queryParam("sort_field", "name")
        .queryParam("sort_direction", "Ascending")
        .queryParam("page_size", 3)
        .queryParam("page_number", 0)
      .when().get()
      .then()
        .statusCode(200)
        .body("content.size()", CoreMatchers.is(3))
        .body("content.get(0).name", CoreMatchers.is("Laranja"))
        .body("pageSize", CoreMatchers.is(3))
        .body("pageNumber", CoreMatchers.is(0));
  }

  @Test
  @Order(5)
  public void shouldReturnOk_WhenFruitsListWithSortFieldNameDescending() {
    RestAssured
      .given()
        .queryParam("sort_field", "name")
        .queryParam("sort_direction", "Descending")
        .queryParam("page_size", 3)
        .queryParam("page_number", 0)
      .when().get()
      .then()
        .statusCode(200)
        .body("content.size()", CoreMatchers.is(3))
        .body("content.get(0).name", CoreMatchers.is("Pera"))
        .body("pageSize", CoreMatchers.is(3))
        .body("pageNumber", CoreMatchers.is(0));
  }

  @Test
  @Order(6)
  public void shouldReturnOk_WhenCountFruits() {
    RestAssured
      .given()
      .when().get("/count")
      .then()
        .statusCode(200)
        .body("count", CoreMatchers.is(3));
  }

  @Test
  @Order(7)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(8)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnNotFound_WhenGetFruitWithAInvalidId() {
    RestAssured
      .given()
      .when().get("/10")
      .then().statusCode(404);
  }

  @Test
  @Order(9)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(10)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(11)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(12)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(13)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(14)
  @TestSecurity(authorizationEnabled = false)
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
  @Order(15)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnCreated_WhenCreateFruitWithAValidInput() {
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

  @Test
  @Order(16)
  public void shouldReturnUnauthorized_WhenGetFruitWithoutAuthentication() {
    RestAssured
      .given()
      .when().get("/1")
      .then().statusCode(401);
  }

  @Test
  @Order(17)
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnOk_WhenGetFruitWithAuthentication() {
    RestAssured
      .given()
      .when().get("/1")
      .then().statusCode(200);
  }

  @Test
  @Order(18)
  public void shouldReturnUnauthorized_WhenCreateFruitWithoutAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().post()
      .then().statusCode(401);
  }

  @Test
  @Order(19)
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnForbidden_WhenCreateFruitWithoutAdminAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Melão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().post()
      .then().statusCode(403);
  }

  @Test
  @Order(20)
  @TestSecurity(user = "admin", roles = { "admin", "user" })
  public void shouldReturnCreated_WhenCreateFruitWithAdminAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Abacate", BigDecimal.valueOf(2));

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

  @Test
  @Order(21)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnBadRequest_WhenUpdateFruitWithAInvalidId() {
    var fruitInputDTO = new FruitInputDTO("Mamão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().put("/10")
      .then().statusCode(400);
  }

  @Test
  @Order(22)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnBaqRequest_WhenUpdateFruitWithABlankName() {
    var fruitInputDTO = new FruitInputDTO("", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().put("/5")
      .then()
        .statusCode(400)
        .body(CoreMatchers.containsString("Name cannot be blank"));
  }

  @Test
  @Order(23)
  public void shouldReturnUnauthorized_WhenUpdateFruitWithoutAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Mamão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().put("/5")
      .then().statusCode(401);
  }

  @Test
  @Order(24)
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnForbidden_WhenUpdateFruitWithoutAdminAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Mamão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().put("/5")
      .then().statusCode(403);
  }

  @Test
  @Order(25)
  @TestSecurity(user = "admin", roles = { "admin", "user" })
  public void shouldReturnOk_WhenUpdateFruitWithAdminAuthentication() {
    var fruitInputDTO = new FruitInputDTO("Mamão", BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitInputDTO)
      .when().put("/5")
      .then()
        .statusCode(200)
        .body("id", CoreMatchers.is(5))
        .body("name", CoreMatchers.is(fruitInputDTO.name()))
        .body("quantity", CoreMatchers.is(fruitInputDTO.quantity().intValue()));
  }

  @Test
  @Order(26)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnBaqRequest_WhenUpdateFruitQuantityWithInvalidName() {
    var fruitQuantityDTO = new FruitQuantityDTO(BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitQuantityDTO)
      .when().patch("/Banana")
      .then().statusCode(400);
  }

  @Test
  @Order(27)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnBaqRequest_WhenUpdateFruitQuantityWithInvalidQuantity() {
    var fruitQuantityDTO = new FruitQuantityDTO(BigDecimal.valueOf(-1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitQuantityDTO)
      .when().patch("/Mamão")
      .then()
        .statusCode(400)
        .body(CoreMatchers.containsString("Quantity must be greater than or igual zero"));
  }

  @Test
  @Order(28)
  public void shouldReturnUnauthorized_WhenUpdateFruitQuantityWithoutAuthentication() {
    var fruitQuantityDTO = new FruitQuantityDTO(BigDecimal.valueOf(1));

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitQuantityDTO)
      .when().patch("/Mamão")
      .then().statusCode(401);
  }

  @Test
  @Order(29)
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnOk_WhenUpdateFruitQuantityWithAuthentication() {
    var fruitQuantityDTO = new FruitQuantityDTO(BigDecimal.ZERO);

    RestAssured
      .given()
        .contentType(ContentType.JSON)
        .body(fruitQuantityDTO)
      .when().patch("/Mamão")
      .then()
        .statusCode(200)
        .body("name", CoreMatchers.is("Mamão"))
        .body("quantity", CoreMatchers.is(0));
  }

  @Test
  @Order(30)
  @TestSecurity(authorizationEnabled = false)
  public void shouldReturnBadRequest_WhenDeleteFruitWithAInvalidId() {
    RestAssured
      .given()
      .when().delete("/10")
      .then().statusCode(400);
  }

  @Test
  @Order(31)
  public void shouldReturnUnauthorized_WhenDeleteFruitWithoutAuthentication() {
    RestAssured
      .given()
      .when().delete("/5")
      .then().statusCode(401);
  }

  @Test
  @Order(32)
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnForbidden_WhenDeleteFruitWithoutAdminAuthentication() {
    RestAssured
      .given()
      .when().delete("/5")
      .then().statusCode(403);
  }

  @Test
  @Order(33)
  @TestSecurity(user = "admin", roles = { "admin", "user" })
  public void shouldReturnNoContent_WhenDeleteFruitWithAdminAuthentication() {
    RestAssured
      .given()
      .when().delete("/5")
      .then().statusCode(204);
  }

}
