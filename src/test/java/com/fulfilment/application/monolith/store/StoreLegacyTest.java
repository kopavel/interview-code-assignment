package com.fulfilment.application.monolith.store;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.stores.legacy.LegacyStoreManagerGateway;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import org.junit.jupiter.api.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreLegacyTest {
  @Inject
  UserTransaction utx;

  @Test
  @Order(1)
  public void testInsertLegacyHandler() throws Exception {
    utx.begin();
    LegacyStoreManagerGateway mockGateway = mock(LegacyStoreManagerGateway.class);
    QuarkusMock.installMockForType(mockGateway, LegacyStoreManagerGateway.class);
    try {
      Store store = new Store("LegacySyncTest");
      store.quantityProductsInStock = 10;
      store.persist();

    } finally {
      utx.commit();
    }

    verify(mockGateway, times(1)).createStoreOnLegacySystem(any());
    verify(mockGateway, times(0)).updateStoreOnLegacySystem(any());
  }

  @Test
  @Order(2)
  public void testUpdateLegacyHandler() throws Exception {
    LegacyStoreManagerGateway mockGateway = mock(LegacyStoreManagerGateway.class);
    QuarkusMock.installMockForType(mockGateway, LegacyStoreManagerGateway.class);
    utx.begin();
    try {
      Store forUpdate = Store.find("name = 'LegacySyncTest'").firstResult();
      forUpdate.name = "LegacySyncTestUpdated";
    } finally {
      utx.commit();
    }
    verify(mockGateway, times(0)).createStoreOnLegacySystem(any());
    verify(mockGateway, times(1)).updateStoreOnLegacySystem(any());
  }

  @Order(3)
  @Test
  public void testDeleteLegacyHandler() throws Exception {
    LegacyStoreManagerGateway mockGateway = mock(LegacyStoreManagerGateway.class);
    QuarkusMock.installMockForType(mockGateway, LegacyStoreManagerGateway.class);
    utx.begin();
    try {
      Store toDelete = Store.find("name = 'LegacySyncTestUpdated'").firstResult();
      toDelete.delete();
    } finally {
      utx.commit();
    }
    verify(mockGateway, times(0)).createStoreOnLegacySystem(any());
    verify(mockGateway, times(0)).updateStoreOnLegacySystem(any());
  }
}
