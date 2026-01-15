package com.fulfilment.application.monolith.location;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class LocationGateway implements LocationResolver {
  private static final Map<String, Location> locations = new HashMap<>();
  static {
    addLocation("ZWOLLE-001", 1, 40);
    addLocation("ZWOLLE-002", 2, 50);
    addLocation("AMSTERDAM-001", 5, 100);
    addLocation("AMSTERDAM-002", 3, 75);
    addLocation("TILBURG-001", 1, 40);
    addLocation("HELMOND-001", 1, 45);
    addLocation("EINDHOVEN-001", 2, 70);
    addLocation("VETSBY-001", 1, 90);
  }

  @Override
  public Location resolveByIdentifier(String identifier) {
    return locations.get(identifier);
  }


  private static void addLocation(String identification, int maxNumberOfWarehouses, int maxCapacity) {
    locations.put(identification, new Location(identification, maxNumberOfWarehouses, maxCapacity));
  }

}
