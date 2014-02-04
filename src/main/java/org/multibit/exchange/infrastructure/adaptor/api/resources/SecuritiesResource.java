package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        new Ticker(securityDescriptor.getTicker()),
        new Currency(securityDescriptor.getBaseCurrency()),
        new Currency(securityDescriptor.getCounterCurrency()));
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
