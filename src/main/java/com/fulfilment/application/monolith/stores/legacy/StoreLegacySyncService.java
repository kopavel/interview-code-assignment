package com.fulfilment.application.monolith.stores.legacy;
import com.fulfilment.application.monolith.stores.Store;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Synchronization;
import jakarta.transaction.TransactionSynchronizationRegistry;

import static jakarta.transaction.Status.STATUS_COMMITTED;

@ApplicationScoped
@Unremovable
public class StoreLegacySyncService {
  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  TransactionSynchronizationRegistry txRegistry;
  @Inject
  LegacyStoreManagerGateway legacyGateway;

  public void syncAfterCommit(Store store, boolean created) {
    txRegistry.registerInterposedSynchronization(new Synchronization() {
      @Override
      public void beforeCompletion() {
      }

      @Override
      public void afterCompletion(int status) {
        if (status == STATUS_COMMITTED) {
          if (created) {
            legacyGateway.createStoreOnLegacySystem(store);
          } else {
            legacyGateway.updateStoreOnLegacySystem(store);
          }
        }
      }
    });
  }
}
