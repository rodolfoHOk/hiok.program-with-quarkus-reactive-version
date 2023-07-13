package dev.hiok.application.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
public class UserResourceTest {

  @Test
  public void shouldReturnUnauthorized_WhenGetUserInfoWithoutAuthentication() {
    RestAssured.given()
      .given()
      .when().get("/me")
      .then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "alice", roles = { "user" })
  public void shouldReturnOk_WhenGetUserInfoWithAuthentication() {
    RestAssured.given()
      .given()
      .when().get("/me")
      .then()
        .statusCode(200)
        .body("username", CoreMatchers.is("alice"))
        .body("roles", CoreMatchers.hasItem("user"));
  }

  @Test
  @TestSecurity(user = "admin", roles = { "admin", "user" })
  public void shouldReturnOk_WhenGetUserInfoWithAdminAuthentication() {
    RestAssured.given()
      .given()
      .when().get("/me")
      .then()
        .statusCode(200)
        .body("username", CoreMatchers.is("admin"))
        .body("roles", CoreMatchers.hasItem("admin"))
        .body("roles", CoreMatchers.hasItem("user"));
  }

}
