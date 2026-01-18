package com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class WarehouseAlreadyExistException extends WebApplicationException {
  public WarehouseAlreadyExistException(String businessUnitCode) {
    super("Warehouse with business unit code " + businessUnitCode + " already exists.", Response.Status.BAD_REQUEST);
  }
}
