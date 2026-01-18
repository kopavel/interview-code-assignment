package com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class StockChangeException extends WebApplicationException {
  public StockChangeException() {
    super("Stock cannot be changed.", Response.Status.BAD_REQUEST);
  }
}
