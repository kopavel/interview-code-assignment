package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

  @Override
  public List<Warehouse> getAll() {
    List<DbWarehouse> dbWarehouses = this.listAll();
    return dbWarehouses.stream().filter(w -> w.archivedAt == null).map(DbWarehouse::toWarehouse).toList();
  }

  @Override
  public void create(Warehouse warehouse) {
    new DbWarehouse(warehouse).persist();
  }

  @Override
  public void update(Warehouse warehouse) {
    DbWarehouse dbWarehouse =
      find("businessUnitCode=?1 and archivedAt is null", warehouse.businessUnitCode).firstResult();
    dbWarehouse.businessUnitCode = warehouse.businessUnitCode;
    dbWarehouse.location = warehouse.location;
    dbWarehouse.capacity = warehouse.capacity;
    dbWarehouse.stock = warehouse.stock;
  }

  @Override
  public void remove(String id) {
    DbWarehouse dbWarehouse =
      find("id=?1 and archivedAt is null", id).firstResult();
    if (dbWarehouse == null) {
      throw new IllegalArgumentException("Warehouse with id " + id + " does not exist.");
    }
    dbWarehouse.archivedAt = LocalDateTime.now();
  }

  @Override
  public Warehouse findByBusinessUnitCode(String buCode) {
    DbWarehouse dbWarehouse = find("businessUnitCode=?1 and archivedAt is null", buCode).firstResult();
    return dbWarehouse == null ? null : dbWarehouse.toWarehouse();
  }

  @Override
  public Capacity getCurrentCapacity(Location location) {
    Capacity capacity = new Capacity();
    for (DbWarehouse dbWarehouse : this.list("location=?1 and archivedAt is null", location.identification)) {
      capacity.currentCapacity += dbWarehouse.capacity;
      capacity.currentNumberOfWarehouses++;
    }
    return capacity;
  }
}
