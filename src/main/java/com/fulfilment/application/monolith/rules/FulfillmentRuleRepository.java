package com.fulfilment.application.monolith.rules;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
public class FulfillmentRuleRepository implements PanacheRepository<DbFulfillmentRule> {
  public void create(DbFulfillmentRule rule) {
    if (find("warehouse = ?1 and store=?2 and product=?3",
             rule.warehouse, rule.store, rule.product).count() > 0) {
      throw new WebApplicationException("Rule already exists", Response.Status.CONFLICT);
    }
    if (countDistinctProductByWarehouse(rule.warehouse) >= 5) {
      throw new WebApplicationException("Each `Warehouse` can store maximally 5 types of `Products`",
                                        Response.Status.PRECONDITION_FAILED);
    }
    if (countDistinctStoresByWarehouse(rule.warehouse) >= 3) {
      throw new WebApplicationException("Each `Store` can be fulfilled by a maximum of 3 different `Warehouses`",
                                        Response.Status.PRECONDITION_FAILED);
    }
    if (countDistinctWarehousesByProductAndStore(rule.store, rule.product) >= 2) {
      throw new WebApplicationException(
        "Each `Product` can be fulfilled by a maximum of 2 different `Warehouses` per `Store`",
        Response.Status.PRECONDITION_FAILED);
    }
    persist(rule);
  }

  public long countDistinctStoresByWarehouse(DbWarehouse warehouse) {
    return find(
      "select count(distinct f.store.id) from " + DbFulfillmentRule.class.getSimpleName() + " f where f.warehouse = ?1",
      warehouse)
      .project(Long.class).firstResult();
  }

  public long countDistinctProductByWarehouse(DbWarehouse warehouse) {
    return find(
      "select count(distinct f.product.id) from " + DbFulfillmentRule.class.getSimpleName() +
        " f where f.warehouse = ?1",
      warehouse)
      .project(Long.class).firstResult();

  }

  public long countDistinctWarehousesByProductAndStore(Store store, Product product) {
    return find("store = ?1 AND product = ?2", store, product).count();
  }

}
