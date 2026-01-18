package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ArchiveWarehouseUseCase implements ArchiveWarehouseOperation {

  @Inject WarehouseStore warehouseStore;

  @Override
  public void archive(String id) {
    warehouseStore.remove(id);
  }
}
