package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
public class ArchiveWarehouseUseCase implements ArchiveWarehouseOperation {

  @Inject WarehouseStore warehouseStore;

  @Override
  public void archive(String id) {
    try {
      warehouseStore.remove(id);
    } catch (IllegalArgumentException e) {
      throw new WebApplicationException("Warehouse with id " + id + " does not exist.", Response.Status.NOT_FOUND);
    }
  }
}
