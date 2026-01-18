package com.fulfilment.application.monolith.warehouses.domain.usecases.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class LocationNotFoundException extends WebApplicationException {
  public LocationNotFoundException(String location) {
    super("Location " + location + " does not exist.", Response.Status.BAD_REQUEST);
  }
}
