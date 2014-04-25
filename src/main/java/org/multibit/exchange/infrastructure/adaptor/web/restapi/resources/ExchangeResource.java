package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderId;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

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

  public static final String MARKET_PRICE = MarketOrder.MARKET_PRICE;

  @Inject
  public ExchangeResource(ExchangeService exchangeService, QueryProcessor readService) {
    this.exchangeService = exchangeService;
    this.readService = readService;
  }

  /**
   * <p>Initializes the exchange</p>
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  public void registerExchange(ExchangeDescriptor exchangeDescriptor) {
    String identifier = exchangeDescriptor.getIdentifier();
    Preconditions.checkArgument(!Strings.isNullOrEmpty(identifier), "identifier must not be null or empty");

    exchangeService.initializeExchange(new ExchangeId(identifier));
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
  @Path("/{exchangeId}/orders")
  public String placeOrder(
      @PathParam("exchangeId") String exchangeId,
      OrderDescriptor orderDescriptor) {
    OrderId orderId = new OrderId();
    exchangeService.placeOrder(new ExchangeId(exchangeId), orderId, orderDescriptor);
    return orderId.getIdentifier();
  }

}
