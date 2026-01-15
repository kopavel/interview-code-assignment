package com.fulfilment.application.monolith.store;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.stores.legacy.LegacyStoreManagerGateway;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class StoreLegacyTest {
  @Inject
  UserTransaction utx;

  @Test
  public void testCreateStore() throws SystemException, NotSupportedException, HeuristicRollbackException,
    HeuristicMixedException, RollbackException {
    utx.begin();
    LegacyStoreManagerGateway mockGateway = mock(LegacyStoreManagerGateway.class);
    QuarkusMock.installMockForType(mockGateway, LegacyStoreManagerGateway.class);
    Store store = new Store("LegacySyncTest");
    store.quantityProductsInStock = 10;
    store.persist();
    utx.commit();
    utx.begin();
    store.name = "newOne";
    utx.commit();
    utx.begin();
    store.delete();
    utx.commit();
    verify(mockGateway, times(1)).createStoreOnLegacySystem(store);
  }
}
