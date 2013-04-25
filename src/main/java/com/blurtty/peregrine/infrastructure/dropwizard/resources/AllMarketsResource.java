package com.blurtty.peregrine.infrastructure.dropwizard.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.blurtty.peregrine.infrastructure.dropwizard.common.BaseResource;

import com.blurtty.peregrine.service.MarketReadService;
import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.readmodel.MarketListReadModel;

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

  private final MarketService marketService;
  private final MarketReadService marketReadService;

  @Inject
  public AllMarketsResource(MarketService marketService, MarketReadService marketReadService) {
    this.marketService = marketService;
    this.marketReadService = marketReadService;
  }

  /**
   * <p>Creates a new market</p>
   *
   * @param marketDescriptor The properties of the market
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void addMarket(MarketDescriptor marketDescriptor) {
    marketService.addMarket(marketDescriptor.getSymbol(), marketDescriptor.getItemSymbol(), marketDescriptor.getCurrencySymbol());
  }


  /**
   * <p>Fetches markets from the market read model</p>
   *
   * @return The list of markets
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  public MarketListReadModel getMarkets() {
    return new MarketListReadModel(marketReadService.fetchMarkets());
  }
}
