package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
public class CreateWarehouseUseCaseTest {
  @Inject CreateWarehouseUseCase createWarehouseUseCase;
  @Inject WarehouseStore warehouseRepository;
  @Inject LocationResolver locationResolver;

  @Test
  public void testArchiveWarehouse() {
    assertFalse(warehouseRepository.getAll()
                                   .stream()
                                   .map(w -> w.businessUnitCode)
                                   .anyMatch(id -> id.equals("CreateTest.001")));

    Warehouse warehouse = new Warehouse();
    warehouse.businessUnitCode = "CreateTest.001";
    warehouse.capacity = 10;
    warehouse.location = locationResolver.resolveByIdentifier("AMSTERDAM-001").identification;
    warehouse.stock = 0;
    createWarehouseUseCase.create(warehouse);
    assertTrue(warehouseRepository.getAll()
                                  .stream()
                                  .map(w -> w.businessUnitCode)
                                  .anyMatch(id -> id.equals("CreateTest.001")));

  }
}
