package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Resource to provide the following to REST clients:</p>
 * <ul>
 * <li>Securities related information and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/exchanges/{exchangeId}/securities")
public class SecuritiesResource extends BaseResource {

  private static Logger LOGGER = LoggerFactory.getLogger(SecuritiesResource.class);

  @Inject
  public SecuritiesResource(ExchangeService exchangeService, ReadService readService) {
    super(exchangeService, readService);
  }

  /**
   * <p>Creates a new security</p>
   *
   * @param securityDescriptor The properties of the security
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  public void addSecurity(
      @PathParam("exchangeId") String exchangeId,
      SecurityDescriptor securityDescriptor) {

    exchangeService.createSecurity(
        new ExchangeId(exchangeId),
        new Ticker(securityDescriptor.getTickerSymbol()),
        new TradeableItem(securityDescriptor.getTradeableItemSymbol()),
        new Currency(securityDescriptor.getCurrencySymbol()));
  }

  /**
   * <p>Fetches list of securities from the read model</p>
   *
   * @return The list of securities
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  public SecurityListReadModel getSecurities(
      @PathParam("exchangeId") String exchangeId) {
    return new SecurityListReadModel(readService.fetchSecurities(exchangeId));
  }

  /**
   * <p>Gets open orders</p>
   *
   * @param tickerSymbol The symbol for the security to fetch orders for
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{ticker}/orderbook")
  public OrderListReadModel getOpenOrders(
      @PathParam("exchangeId") String exchangeId,
      @PathParam("ticker") String tickerSymbol) {
    List<OrderReadModel> orderList = readService.fetchOpenOrders(tickerSymbol);
    return new OrderListReadModel(tickerSymbol, orderList);
  }

}
