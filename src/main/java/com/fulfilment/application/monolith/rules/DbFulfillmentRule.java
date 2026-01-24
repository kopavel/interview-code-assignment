package com.fulfilment.application.monolith.rules;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(
  name = "fulfillmentRule",
  uniqueConstraints = @UniqueConstraint(
    columnNames = {"store_id", "product_id", "warehouse_id"}
  )
)
@Cacheable
public class DbFulfillmentRule extends PanacheEntity {
  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  public DbWarehouse warehouse;
  @ManyToOne
  @JoinColumn(name = "store_id")
  public Store store;
  @ManyToOne
  @JoinColumn(name = "product_id")
  public Product product;

  public DbFulfillmentRule(Long id, Warehouse warehouse, Store store, Product product) {
    this.id = id;
    this.store = store;
    this.product = product;
    this.warehouse = DbWarehouse.find("businessUnitCode=?1", warehouse.businessUnitCode).firstResult();
  }

  public DbFulfillmentRule() {

  }

  FulfillmentRule toApi() {
    return new FulfillmentRule(id,warehouse.toWarehouse(), store, product);
  }
}
