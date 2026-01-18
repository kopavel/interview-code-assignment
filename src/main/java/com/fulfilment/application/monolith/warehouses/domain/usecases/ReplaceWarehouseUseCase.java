package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.Capacity;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  @Inject WarehouseStore warehouseStore;
  @Inject LocationResolver locationResolver;

  @Override
  public Warehouse replace(Warehouse newWarehouse) {
    Warehouse oldWarehouse = warehouseStore.findByBusinessUnitCode(newWarehouse.businessUnitCode);
    if (oldWarehouse == null) {
      throw new WarehouseNotWoundException(newWarehouse.businessUnitCode);
    }
    if (!oldWarehouse.stock.equals(newWarehouse.stock)) {
      throw new StockChangeException();
    }
    Location location = locationResolver.resolveByIdentifier(newWarehouse.location);
    if (location == null) {
      throw new LocationNotFoundException(newWarehouse.location);
    }
    Capacity currentCapacity = warehouseStore.getCurrentCapacity(location);
    if (!oldWarehouse.location.equals(newWarehouse.location)) {
      if (currentCapacity.currentNumberOfWarehouses == location.maxNumberOfWarehouses ||
        currentCapacity.currentCapacity + newWarehouse.capacity > location.maxCapacity) {
        throw new LocationIsFullException(location.identification);
      }
    } else {
      if (currentCapacity.currentCapacity - oldWarehouse.capacity + newWarehouse.capacity > location.maxCapacity) {
        throw new LocationIsFullException(location.identification);
      }
    }

    warehouseStore.update(newWarehouse);
    return oldWarehouse;
  }
}
