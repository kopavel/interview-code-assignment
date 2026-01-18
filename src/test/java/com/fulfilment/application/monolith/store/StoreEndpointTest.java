package com.fulfilment.application.monolith.store;

import com.fulfilment.application.monolith.stores.Store;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
public class StoreEndpointTest {

  @Test
  public void testCrudProduct() {
    final String path = "stores";

    // List all, should have all 3 products the database has initially:
    given()
      .when()
      .get(path)
      .then()
      .statusCode(200)
      .body(containsString("TONSTAD"), containsString("KALLAX"), containsString("BESTÅ"));

    // Delete the TONSTAD:
    given().when().delete(path + "/1").then().statusCode(204);

    // List all, TONSTAD should be missing now:
    given()
      .when()
      .get(path)
      .then()
      .statusCode(200)
      .body(not(containsString("TONSTAD")), containsString("KALLAX"), containsString("BESTÅ"));

    //update
    Store entity = new Store();
    entity.name = "KALLA2";
    entity.quantityProductsInStock = 5;
    given()
      .contentType(JSON)
      .body(entity)
      .when()
      .put(path + "/2")
      .then()
      .statusCode(200);
    given()
      .when()
      .get(path + "/2")
      .then()
      .statusCode(200)
      .body(
        not(containsString("KALLAX")),
        containsString("KALLA2"), containsString(":5}"));

    //patch
    entity.quantityProductsInStock = 10;
    given()
      .contentType(JSON)
      .body(entity)
      .when()
      .patch(path + "/2")
      .then()
      .statusCode(200);
    given()
      .when()
      .get(path + "/2")
      .then()
      .statusCode(200)
      .body(
        not(containsString(":5}")), containsString(":10}"));

    //exception
    given()
      .when()
      .get(path + "/112")
      .then()
      .statusCode(404)
      .body(
        containsString("Store with id of 112 does not exist."));
  }

}
