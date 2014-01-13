package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.ItemQuantity;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Resource to provide the following to REST clients:</p>
 * <ul>
 * <li>Exchange related data and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/exchanges")
public class ExchangeResource extends BaseResource {

  private static Logger LOGGER = LoggerFactory.getLogger(ExchangeResource.class);

  @Inject
  public ExchangeResource(ExchangeService exchangeService, ReadService readService) {
    super(exchangeService, readService);
  }

  /**
   * <p>Initializes the exchange</p>
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  public void initializeExchange(ExchangeDescriptor exchangeDescriptor) {
    exchangeService.initializeExchange(new ExchangeId(exchangeDescriptor.getIdentifier()));
  }


  /**
   * <p>Places a buy (bid) order</p>
   *
   * @param exchangeId      The exchange to place the order on
   * @param orderDescriptor The order details
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{exchangeId}/bids")
  public void placeBuyOrder(
      @PathParam("exchangeId") String exchangeId,
      BuyOrderDescriptor orderDescriptor) {
    exchangeService.placeBuyOrder(
        new ExchangeId(exchangeId),
        new Ticker(orderDescriptor.getTickerSymbol()),
        new ItemQuantity(orderDescriptor.getOrderAmount()));
  }

    /**
     * <p>Places a Sell order</p>
     *
     * @param exchangeId      The exchange to place the order on
     * @param orderDescriptor The order details
     */
    @POST
    @Timed
    @CacheControl(noCache = true)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{exchangeId}/asks")
    public void placeSellOrder(
            @PathParam("exchangeId") String exchangeId,
            SellOrderDescriptor orderDescriptor) {
        exchangeService.placeSellOrder(
                new ExchangeId(exchangeId),
                new Ticker(orderDescriptor.getTickerSymbol()),
                new ItemQuantity(orderDescriptor.getOrderAmount()));
    }

}
