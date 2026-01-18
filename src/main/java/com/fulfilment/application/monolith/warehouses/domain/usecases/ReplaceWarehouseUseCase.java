package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.Capacity;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Transactional
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  @Inject WarehouseStore warehouseStore;
  @Inject LocationResolver locationResolver;


  @Override
  public Warehouse replace(Warehouse newWarehouse) {
    Warehouse oldWarehouse = warehouseStore.findByBusinessUnitCode(newWarehouse.businessUnitCode);
    if (oldWarehouse == null) {
      throw new WebApplicationException("Warehouse not found.", NOT_FOUND);
    }
    if (!oldWarehouse.stock.equals(newWarehouse.stock)) {
      throw new WebApplicationException("Stock cannot be changed.", BAD_REQUEST);
    }
    if (!oldWarehouse.location.equals(newWarehouse.location)) {
      Location location = locationResolver.resolveByIdentifier(newWarehouse.location);
      if (location == null) {
        throw new WebApplicationException("Location not found.", BAD_REQUEST);
      }
      Capacity currentCapacity = warehouseStore.getCurrentCapacity(location);
      if (currentCapacity.currentNumberOfWarehouses == location.maxNumberOfWarehouses ||
        currentCapacity.currentCapacity + newWarehouse.capacity > location.maxCapacity) {
        throw new WebApplicationException("Location is full.", BAD_REQUEST);
      }
    }
    warehouseStore.update(newWarehouse);
    return oldWarehouse;
  }
}
