package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationIsFullException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.LocationNotFoundException;
import com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions.StockChangeException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.wildfly.common.Assert.assertNotNull;
import static org.wildfly.common.Assert.assertTrue;

@QuarkusTest
public class ReplaceWarehouseUseCaseTest {
  @Inject ReplaceWarehouseUseCase replaceWarehouseUseCase;
  @Inject WarehouseStore warehouseRepository;
  @Inject LocationResolver locationResolver;

  @Test
  @TestTransaction
  public void testReplaceWarehouse() {
    assertTrue(warehouseRepository.getAll()
                                  .stream()
                                  .map(w -> w.businessUnitCode)
                                  .anyMatch(id -> id.equals("MWH.012")));
    Warehouse warehouse = new Warehouse();
    warehouse.businessUnitCode = "MWH.012";
    warehouse.capacity = 50;
    warehouse.location = "AMSTERDAM-001";
    warehouse.stock = 10;
    assertThrowsExactly(StockChangeException.class, () -> replaceWarehouseUseCase.replace(warehouse));
    warehouse.stock = 5;
    warehouse.location = "AMSTERDAM-111";
    assertThrowsExactly(LocationNotFoundException.class, () -> replaceWarehouseUseCase.replace(warehouse));
    warehouse.location = "AMSTERDAM-002";
    warehouse.capacity = 150;
    assertThrowsExactly(LocationIsFullException.class, () -> replaceWarehouseUseCase.replace(warehouse));
    warehouse.capacity = 75;
    Warehouse replace = replaceWarehouseUseCase.replace(warehouse);
    assertNotNull(replace);
  }
}
