package com.fulfilment.application.monolith.location;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class LocationGatewayTest {

  @Inject
  LocationResolver locationGateway;

  @Test
  public void testSuccessLocationResolver() {
    Location location = locationGateway.resolveByIdentifier("ZWOLLE-001");
    assertEquals("ZWOLLE-001", location.identification);
  }

  @Test
  public void testFaliedLocationResolver() {
    Location location = locationGateway.resolveByIdentifier("ZWOLLE-999");
    assertNull(location);
  }
}
