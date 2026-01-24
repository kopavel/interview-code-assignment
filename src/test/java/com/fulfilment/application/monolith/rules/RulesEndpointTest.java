package com.fulfilment.application.monolith.rules;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
public class RulesEndpointTest {

  @Test
  public void testCrudProduct() {
    final String path = "rules";

    // List all, should have all 3 products the database has initially:
    given()
      .when()
      .get(path)
      .then()
      .statusCode(200);

    //Create
    Store store = new Store();
    store.id=2L;
    Warehouse warehouse = new Warehouse();
    warehouse.businessUnitCode="MWH.012";
    Product product = new Product();
    product.id=2L;
    FulfillmentRule newRule = new FulfillmentRule(null, warehouse,store, product);
    given()
      .when()
      .contentType(JSON)
      .body(newRule)
      .post(path)
      .then()
      .statusCode(200);

    given()
      .when()
      .get(path)
      .then()
      .statusCode(200)
      .body(containsString("MWH.012"));

    given()
      .when()
      .contentType(JSON)
      .body(newRule)
      .post(path)
      .then()
      .statusCode(409);

    given()
      .when()
      .delete(path + "/5")
      .then()
      .statusCode(404);

    given()
      .when()
      .delete(path + "/1")
      .then()
      .statusCode(204);

    given()
      .when()
      .get(path)
      .then()
      .statusCode(200)
      .body(not(containsString("MWH.012")));

  }

}
