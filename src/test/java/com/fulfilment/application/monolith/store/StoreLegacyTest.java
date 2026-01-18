package com.fulfilment.application.monolith.store;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.stores.legacy.LegacyStoreManagerGateway;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class StoreLegacyTest {
  @Inject
  UserTransaction utx;

  @Test
  public void testCreateStore() throws Exception {

    utx.begin();
    LegacyStoreManagerGateway mockGateway = mock(LegacyStoreManagerGateway.class);
    QuarkusMock.installMockForType(mockGateway, LegacyStoreManagerGateway.class);

    //INSERT
    Store store = new Store("LegacySyncTest");
    store.quantityProductsInStock = 10;
    store.persist();
    utx.commit();

    // UPDATE
    utx.begin();
    Store managed = Store.findById(store.id);
    managed.name = "LegacySyncTestUpdated";
    utx.commit();

    // DELETE
    utx.begin();
    Store toDelete = Store.findById(store.id);
    toDelete.delete();
    utx.commit();

    verify(mockGateway, times(1)).createStoreOnLegacySystem(any());
    verify(mockGateway, times(1)).updateStoreOnLegacySystem(any());
  }
}
