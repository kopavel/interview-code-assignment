package com.fulfilment.application.monolith.rules;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/rules")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Transactional
public class FulfillmentRuleResource {

  @Inject FulfillmentRuleRepository repository;

  @GET
  public List<FulfillmentRule> listAll() {
    return repository.listAll().stream().map(DbFulfillmentRule::toApi).toList();
  }

  @POST
  public FulfillmentRule create(FulfillmentRule rule) {
    repository.create(rule.toDb());
    return rule;
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    DbFulfillmentRule rule = repository.findById(id);
    if (rule == null) {
      throw new WebApplicationException(404);
    }
    repository.delete(rule);
  }
}
