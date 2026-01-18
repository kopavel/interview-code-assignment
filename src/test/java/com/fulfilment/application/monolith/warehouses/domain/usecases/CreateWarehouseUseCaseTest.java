package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationIsFullException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationNotFoundException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.WarehouseAlreadyExistException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
public class CreateWarehouseUseCaseTest {
  @Inject CreateWarehouseUseCase createWarehouseUseCase;
  @Inject WarehouseStore warehouseRepository;
  @Inject LocationResolver locationResolver;

  @Test
  @TestTransaction
  public void testArchiveWarehouse() {
    assertFalse(warehouseRepository.getAll()
                                   .stream()
                                   .map(w -> w.businessUnitCode)
                                   .anyMatch(id -> id.equals("CreateTest.001")));

    Warehouse warehouse = new Warehouse();
    warehouse.businessUnitCode = "MWH.012";
    warehouse.capacity = 100;
    warehouse.location = "AMSTERDAM-001";
    warehouse.stock = 0;
    assertThrowsExactly(WarehouseAlreadyExistException.class, () -> createWarehouseUseCase.create(warehouse));
    warehouse.businessUnitCode = "CreateTest.001";
    assertThrowsExactly(LocationIsFullException.class, () -> createWarehouseUseCase.create(warehouse));
    warehouse.location = "notexist";
    assertThrowsExactly(LocationNotFoundException.class, () -> createWarehouseUseCase.create(warehouse));
    warehouse.location = "AMSTERDAM-001";
    warehouse.capacity = 50;
    createWarehouseUseCase.create(warehouse);
    assertTrue(warehouseRepository.getAll()
                                  .stream()
                                  .map(w -> w.businessUnitCode)
                                  .anyMatch(id -> id.equals("CreateTest.001")));

  }
}
