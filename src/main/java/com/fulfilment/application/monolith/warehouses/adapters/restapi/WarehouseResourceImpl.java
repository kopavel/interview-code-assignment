package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.usecases.ArchiveWarehouseUseCase;
import com.fulfilment.application.monolith.warehouses.domain.usecases.CreateWarehouseUseCase;
import com.fulfilment.application.monolith.warehouses.domain.usecases.ReplaceWarehouseUseCase;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.Warehouse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Transactional
public class WarehouseResourceImpl implements WarehouseResource {
  @Inject CreateWarehouseUseCase createWarehouseUseCase;
  @Inject ReplaceWarehouseUseCase replaceWarehouseUseCase;
  @Inject ArchiveWarehouseUseCase archiveWarehouseUseCase;
  @Inject WarehouseRepository warehouseRepository;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseRepository.getAll().stream().map(this::toApiWarehouse).toList();
  }

  @ResponseStatus(201)
  @Override
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    createWarehouseUseCase.create(toDomainWarehouse(data));
    return data;
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String id) {
    DbWarehouse warehouse = warehouseRepository.findById(Long.parseLong(id));
    if (warehouse == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return toApiWarehouse(warehouse.toWarehouse());
  }

  @Override
  @ResponseStatus(204)
  public void archiveAWarehouseUnitByID(String id) {
    archiveWarehouseUseCase.archive(id);
  }

  @Override
  public Warehouse replaceTheCurrentActiveWarehouse(String businessUnitCode, Warehouse warehouse) {
    return toApiWarehouse(replaceWarehouseUseCase.replace(toDomainWarehouse(warehouse)));
  }

  private com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomainWarehouse(@NotNull Warehouse data) {
    com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse =
      new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();
    warehouse.businessUnitCode = data.getBusinessUnitCode();
    warehouse.capacity = data.getCapacity();
    warehouse.location = data.getLocation();
    warehouse.stock = data.getStock();
    return warehouse;
  }

  private Warehouse toApiWarehouse(
    com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {
    Warehouse response = new Warehouse();
    response.setBusinessUnitCode(warehouse.businessUnitCode);
    response.setLocation(warehouse.location);
    response.setCapacity(warehouse.capacity);
    response.setStock(warehouse.stock);
    return response;
  }

}
