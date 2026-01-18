package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
public class ArchiveWarehouseUseCaseTest {
  @Inject ArchiveWarehouseUseCase archiveWarehouseUseCase;
  @Inject WarehouseStore warehouseRepository;

  @Test
  public void testArchiveWarehouse() {
    assertTrue(warehouseRepository.getAll()
                                  .stream()
                                  .map(w -> w.businessUnitCode)
                                  .anyMatch(id -> id.equals("MWH.001")));
    archiveWarehouseUseCase.archive("1");
    assertFalse(warehouseRepository.getAll()
                                   .stream()
                                   .map(w -> w.businessUnitCode)
                                   .anyMatch(id -> id.equals("MWH.001")));

  }
}
