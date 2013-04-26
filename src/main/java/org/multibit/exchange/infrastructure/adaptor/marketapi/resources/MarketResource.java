package org.multibit.exchange.infrastructure.adaptor.marketapi.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.readmodel.MarketListReadModel;
import org.multibit.exchange.service.MarketReadService;
import org.multibit.exchange.service.MarketService;

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
public class MarketResource extends BaseResource {

  private final MarketService marketService;
  private final MarketReadService marketReadService;

  @Inject
  public MarketResource(MarketService marketService, MarketReadService marketReadService) {
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
  public void addMarket(MarketDescriptor marketDescriptor) {
    marketService.addMarket(marketDescriptor.getMarketSymbol(), marketDescriptor.getItemSymbol(), marketDescriptor.getCurrencySymbol());
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
