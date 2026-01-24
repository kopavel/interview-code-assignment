package com.fulfilment.application.monolith.rules;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class FulfillmentRuleRepository implements PanacheRepository<DbFulfillmentRule> {
  public long countDistinctStoresByWarehouse(DbWarehouse warehouse) {
    return find(
      "select count(distinct f.store.id) from " + DbFulfillmentRule.class.getSimpleName() + " f where f.warehouse = ?1",
      warehouse)
      .project(Long.class).firstResult();
  }

  public long countDistinctProductByWarehouse(DbWarehouse warehouse) {
    return find(
      "select count(distinct f.product.id) from " + DbFulfillmentRule.class.getSimpleName() + " f where f.warehouse = ?1",
      warehouse)
      .project(Long.class).firstResult();

  }

  public long countDistinctWarehousesByProductAndStore(Store store, Product product) {
    return find("store = ?1 AND product = ?2", store, product).count();
  }

}
