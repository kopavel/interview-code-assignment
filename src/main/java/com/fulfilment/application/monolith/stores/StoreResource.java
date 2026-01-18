package com.fulfilment.application.monolith.stores;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("stores")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Transactional
public class StoreResource {

  @GET
  public List<Store> get() {
    return Store.listAll(Sort.by("name"));
  }

  @GET
  @Path("{id}")
  public Store getSingle(@PathParam("id") Long id) {
    Store entity = Store.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }
    return entity;
  }

  @POST
  public Response create(Store store) {
    if (store.id != null) {
      throw new WebApplicationException("Id was invalidly set on request.", 422);
    }

    store.persist();
    return Response.ok(store).status(201).build();
  }

  @PUT
  @Path("{id}")
  public Store update(@PathParam("id") Long id, Store updatedStore) {
    if (updatedStore.name == null) {
      throw new WebApplicationException("Store Name was not set on request.", 422);
    }

    Store entity = Store.findById(id);

    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }

    entity.name = updatedStore.name;
    entity.quantityProductsInStock = updatedStore.quantityProductsInStock;
    return entity;
  }

  @PATCH
  @Path("{id}")
  public Store patch(@PathParam("id") Long id, Store updatedStore) {
    if (updatedStore.name == null) {
      throw new WebApplicationException("Store Name was not set on request.", 422);
    }

    Store entity = Store.findById(id);

    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }

    if (updatedStore.name != null) {
      entity.name = updatedStore.name;
    }

    if (updatedStore.quantityProductsInStock != 0) {
      entity.quantityProductsInStock = updatedStore.quantityProductsInStock;
    }
    return entity;
  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam("id") Long id) {
    Store entity = Store.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }
    entity.delete();
    return Response.status(204).build();
  }

}
