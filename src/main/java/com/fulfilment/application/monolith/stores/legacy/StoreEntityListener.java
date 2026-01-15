package com.fulfilment.application.monolith.stores.legacy;
import com.fulfilment.application.monolith.stores.Store;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class StoreEntityListener {
  private static final StoreLegacySyncService service = CDI.current().select(StoreLegacySyncService.class).get();

  @PostPersist
  public void onCreate(Store store) {
    service.syncAfterCommit(store, true);
  }

  @PostUpdate
  public void onUpdate(Store store) {
    service.syncAfterCommit(store, false);
  }
}
