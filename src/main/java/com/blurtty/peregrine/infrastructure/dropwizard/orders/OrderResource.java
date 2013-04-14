package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.blurtty.peregrine.service.DefaultApplicationService;
import com.google.inject.Inject;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of orders relating to orders</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final DefaultApplicationService appService;
    private final OrderReadService orderReadService;

    @Inject
    public OrderResource(DefaultApplicationService appService, OrderReadService orderReadService) {
      this.appService = appService;
      this.orderReadService = orderReadService;
    }


    @GET
    @Timed
    @CacheControl(noCache = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Orderbook getDefaultOrderbook() {
      return new Orderbook();
    }

    /**
     * @return The default helper list
     */
    @GET
    @Timed
    @Path("/{symbol}")
    @CacheControl(noCache = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Orderbook getOrderbook() {
      return new Orderbook();
    }
  }
