package com.fulfilment.application.monolith.rules;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

public class FulfillmentRule {
  public Long id;
  public Warehouse warehouse;
  public Store store;
  public Product product;

  public FulfillmentRule(Long id, Warehouse warehouse, Store store, Product product) {
    this.id = id;
    this.warehouse = warehouse;
    this.store = store;
    this.product = product;
  }

  public DbFulfillmentRule toDb() {
    return new DbFulfillmentRule(id, warehouse, store, product);
  }
}
