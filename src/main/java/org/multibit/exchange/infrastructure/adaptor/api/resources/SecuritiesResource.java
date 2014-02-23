package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ExchangeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

  @Inject
  public SecuritiesResource(ExchangeService exchangeService, ReadService readService) {
    this.exchangeService = exchangeService;
    this.readService = readService;
  }

  /**
   * <p>Creates a new currency pair</p>
   *
   * @param currencyPairDescriptor The properties of the currency pair
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  public void addCurrencyPair(
      @PathParam("exchangeId") String idString,
      CurrencyPairDescriptor currencyPairDescriptor) {

    ExchangeId exchangeId = new ExchangeId(idString);
    Currency baseCurrency = new Currency(currencyPairDescriptor.getBaseCurrency());
    Currency counterCurrency = new Currency(currencyPairDescriptor.getCounterCurrency());
    CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);
    exchangeService.registerCurrencyPair(exchangeId, currencyPair);
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
