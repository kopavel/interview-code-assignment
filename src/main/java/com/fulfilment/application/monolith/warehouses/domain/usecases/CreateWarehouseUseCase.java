package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.Capacity;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationIsFullException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationNotFoundException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.WarehouseAlreadyExistException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  @Inject WarehouseStore warehouseStore;
  @Inject LocationResolver locationResolver;

  @Override
  public void create(Warehouse warehouse) {
    Warehouse byBusinessUnitCode = warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode);
    if (byBusinessUnitCode != null) {
      throw new WarehouseAlreadyExistException(warehouse.businessUnitCode);
    }
    Location location = locationResolver.resolveByIdentifier(warehouse.location);
    if (location == null) {
      throw new LocationNotFoundException(warehouse.location);
    }
    Capacity currentCapacity = warehouseStore.getCurrentCapacity(location);
    if (currentCapacity.currentNumberOfWarehouses + 1 > location.maxNumberOfWarehouses ||
      currentCapacity.currentCapacity + warehouse.capacity > location.maxCapacity) {
      throw new LocationIsFullException(warehouse.location);
    }
    warehouseStore.create(warehouse);
  }
}
