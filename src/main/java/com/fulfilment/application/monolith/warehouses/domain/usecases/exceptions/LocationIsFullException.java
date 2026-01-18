package com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class LocationIsFullException extends WebApplicationException {
  public LocationIsFullException(String location) {
    super("Location " + location + " is full", Response.Status.BAD_REQUEST);
  }
}
