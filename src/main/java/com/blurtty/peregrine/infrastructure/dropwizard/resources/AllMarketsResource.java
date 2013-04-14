package com.blurtty.peregrine.infrastructure.dropwizard.resources;

import com.blurtty.peregrine.infrastructure.dropwizard.common.BaseResource;
import com.blurtty.peregrine.service.ApplicationService;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * <p>Resource to provide the following to the application:</p>
 * <ul>
 * <li>Market related information and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/markets")
@Produces(MediaType.APPLICATION_JSON)
public class AllMarketsResource extends BaseResource {

  private final ApplicationService appService;

  public AllMarketsResource(ApplicationService appService) {
    this.appService = appService;
  }

  /**
   * <p>Creates a new market</p>
   *
   * @param marketDescriptor - the properties of the market
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void addMarket(MarketDescriptor marketDescriptor) {
    appService.addMarket(marketDescriptor.getSymbol(), marketDescriptor.getItemSymbol(), marketDescriptor.getCurrencySymbol());
  }
}
