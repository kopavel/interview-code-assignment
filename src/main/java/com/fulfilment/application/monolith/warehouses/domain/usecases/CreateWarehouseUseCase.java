package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.Capacity;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  @Inject WarehouseStore warehouseStore;
  @Inject LocationResolver locationResolver;

  @Override
  public void create(Warehouse warehouse) {
    Warehouse byBusinessUnitCode = warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode);
    if (byBusinessUnitCode != null) {
      throw new WebApplicationException(
        "Warehouse with business unit code " + warehouse.businessUnitCode + " already exists.",
        Response.Status.BAD_REQUEST);
    }
    Location location = locationResolver.resolveByIdentifier(warehouse.location);
    if (location == null) {
      throw new WebApplicationException("Location " + warehouse.location + " does not exist.",
                                        Response.Status.BAD_REQUEST);
    }
    Capacity currentCapacity = warehouseStore.getCurrentCapacity(location);
    if (currentCapacity.currentNumberOfWarehouses + 1 > location.maxNumberOfWarehouses ||
      currentCapacity.currentCapacity + warehouse.capacity > location.maxCapacity) {
      throw new WebApplicationException("Location " + warehouse.location + " is full.", Response.Status.BAD_REQUEST);
    }
    warehouseStore.create(warehouse);
  }
}
