package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
public class ArchiveWarehouseUseCaseTest {
  @Inject ArchiveWarehouseUseCase archiveWarehouseUseCase;
  @Inject WarehouseStore warehouseRepository;

  @Test
  public void testSuccessArchiveWarehouse() {
    assertTrue(warehouseRepository.getAll()
                                  .stream()
                                  .map(w -> w.businessUnitCode)
                                  .anyMatch(id -> id.equals("MWH.012")));
    archiveWarehouseUseCase.archive("2");
    assertFalse(warehouseRepository.getAll()
                                   .stream()
                                   .map(w -> w.businessUnitCode)
                                   .anyMatch(id -> id.equals("MWH.012")));
  }

  @Test
  public void testFailedArchiveWarehouse() {
    assertThrowsExactly(WebApplicationException.class, () -> archiveWarehouseUseCase.archive("100"));
  }
}
