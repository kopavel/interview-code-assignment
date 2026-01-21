package com.fulfilment.application.monolith.products;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
public class ProductEndpointTest {

  @Test
  public void testCrudProduct() {
    final String path = "product";

    // List all, should have all 3 products the database has initially:
    given()
      .when()
      .get(path)
      .then()
      .statusCode(200)
      .body(containsString("TONSTAD"), containsString("KALLAX"), containsString("BESTÅ"));

    //Create
    Product newProd = new Product("NewProd");
    given()
      .when()
      .contentType(JSON)
      .body(newProd)
      .post(path)
      .then()
      .statusCode(201)
      .body(containsString("NewProd"));

    //update
    newProd.name = "UpdatedProd";
    given()
      .when()
      .contentType(JSON)
      .body(newProd)
      .put(path+"/4")
      .then()
      .statusCode(200)
      .body(containsString("UpdatedProd"));

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
    Product entity = new Product("KALLA2");
    entity.stock = 10;
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
        containsString("KALLA2"),
        not(containsString(":5}")),
        containsString(":10}"));

    //Exception
    given()
      .when()
      .get(path + "/112")
      .then()
      .statusCode(404)
      .body(
        containsString("Product with id of 112 does not exist."));
  }

}
