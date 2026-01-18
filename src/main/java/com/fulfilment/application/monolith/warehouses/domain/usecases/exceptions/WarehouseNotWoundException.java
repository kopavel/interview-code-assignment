package com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class WarehouseNotWoundException extends WebApplicationException {
  public WarehouseNotWoundException(String id) {
    super("Warehouse " + id + " does not exist.", Response.Status.NOT_FOUND);
  }
}
