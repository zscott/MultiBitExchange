package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import com.yammer.dropwizard.assets.ResourceNotFoundException;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.SecurityListViewModel;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.presentation.model.marketdepth.MarketDepthPresentationModel;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>Resource to provide the following to REST clients:</p>
 * <ul>
 * <li>Securities related information and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
@Path("/exchanges/{exchangeId}/pairs")
public class CurrencyPairsResource extends BaseResource {

  @Inject
  public CurrencyPairsResource(ExchangeService exchangeService, QueryProcessor readService) {
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
  public void add(
      @PathParam("exchangeId") String idString,
      CurrencyPairDescriptor currencyPairDescriptor) {

    ExchangeId exchangeId = new ExchangeId(idString);
    CurrencyPairId currencyPairId = new CurrencyPairId(currencyPairDescriptor.getSymbol());
    exchangeService.registerCurrencyPair(exchangeId, currencyPairId, currencyPairDescriptor.getBaseCurrency(), currencyPairDescriptor.getCounterCurrency());
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
  public SecurityListViewModel getAll(
      @PathParam("exchangeId") String exchangeId) {
    return new SecurityListViewModel(readService.fetchSecurities(exchangeId));
  }

  /**
   * <p>Gets market depth for a currency pair.</p>
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{base}/{counter}/market_depth")
  public MarketDepthPresentationModel getMarketDepth(
      @PathParam("exchangeId") String exchangeId,
      @PathParam("base") String baseCurrencySymbol,
      @PathParam("counter") String counterCurrencySymbol) {
    Currency baseCurrency = new Currency(baseCurrencySymbol);
    Currency counterCurrency = new Currency(counterCurrencySymbol);
    CurrencyPair pair = new CurrencyPair(baseCurrency, counterCurrency);
    String tickerSymbol = pair.getTicker().getSymbol();

    MarketDepthPresentationModel model = readService.fetchMarketDepth(exchangeId, new CurrencyPairId(tickerSymbol));
    if (model == null) {
      throw new ResourceNotFoundException(null);
    }
    return model;
  }
}
