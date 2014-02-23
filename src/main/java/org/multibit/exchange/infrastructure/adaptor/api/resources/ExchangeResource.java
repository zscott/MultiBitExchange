package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.GatewayProxyFactory;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.SecurityOrderId;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;

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
  public ExchangeResource(CommandBus commandBus, RetryScheduler retryScheduler, ReadService readService) {
    GatewayProxyFactory factory = new GatewayProxyFactory(commandBus, retryScheduler);
    exchangeService = factory.createGateway(ExchangeService.class);
    this.readService = readService;
  }

  public ExchangeResource(ExchangeService exchangeService, ReadService readService) {
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
  @Path("/{exchangeId}/orders")
  public String placeOrder(
      @PathParam("exchangeId") String exchangeId,
      OrderDescriptor orderDescriptor) {
    SecurityOrder order = buildOrder(orderDescriptor);
    exchangeService.placeOrder(new ExchangeId(exchangeId), order);
    return order.getId().getRawId();
  }

  private SecurityOrder buildOrder(OrderDescriptor orderDescriptor) {
    SecurityOrder retVal;
    if (orderDescriptor.getPrice().equals(MARKET_PRICE)) {
      retVal = new MarketOrder(
          SecurityOrderId.next(),
          orderDescriptor.getBroker(),
          Side.valueOf(orderDescriptor.getSide().toUpperCase()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()));
    } else {
      retVal = new LimitOrder(
          SecurityOrderId.next(),
          orderDescriptor.getBroker(),
          Side.valueOf(orderDescriptor.getSide().toUpperCase()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()),
          new ItemPrice(orderDescriptor.getPrice()));
    }
    return retVal;
  }
}
