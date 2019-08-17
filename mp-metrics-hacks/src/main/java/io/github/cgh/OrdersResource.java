package io.github.cgh;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Collectors;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    OrderService orderService;

    @Context
    UriInfo uriInfo;

    @GET
    public Response orders() {
        return Response.ok(orderService.orders().collect(Collectors.toList())).build();
    }

    @POST
    @Path("/{name}")
    public Response create(@PathParam("name") String name) {
        orderService.order(name);
        return Response.created(uriInfo.getBaseUri()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        Order order = orderService.getOrder(id);
        return order != null ? Response.ok(order).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
