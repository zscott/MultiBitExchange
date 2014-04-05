package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeTestFixture;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A base test class for testing resources against a simulated container
 */
public abstract class BaseResourceTest {

  protected ExchangeService exchangeService = mock(ExchangeService.class);
  protected QueryProcessor readService = mock(QueryProcessor.class);

  protected CurrencyPairsResource currencyPairsResource = new CurrencyPairsResource(exchangeService, readService);
  protected ExchangeResource exchangeResource = new ExchangeResource(exchangeService, readService);

  protected HttpHeaders httpHeaders;

  private static final DateTime JAN_1_2000_MIDNIGHT_UTC = new DateTime(2000, 1, 1, 0, 0, 0, 0).withZone(DateTimeZone.UTC);

  static {
    // Fix all DateTime values to midnight on January 01 2000 UTC
    DateTimeUtils.setCurrentMillisFixed(JAN_1_2000_MIDNIGHT_UTC.getMillis());
  }

  protected ExchangeTestFixture fixture;

  /**
   * @param acceptableMediaTypes An optional list of acceptable media types with default of JSON
   */
  protected void setUpHttpHeaders(Optional<List<MediaType>> acceptableMediaTypes) {
    if (!acceptableMediaTypes.isPresent()) {
      List<MediaType> defaultAcceptableMediaTypes = Lists.newArrayList();
      defaultAcceptableMediaTypes.add(MediaType.APPLICATION_JSON_TYPE);
      acceptableMediaTypes = Optional.of(defaultAcceptableMediaTypes);
    }
    httpHeaders = mock(HttpHeaders.class);
    when(httpHeaders.getAcceptableMediaTypes()).thenReturn(acceptableMediaTypes.get());
  }

  @Before
  public void setUp() {
    fixture = new ExchangeTestFixture();
  }

  protected String getExchangeIdName() {
    return fixture.getExchangeId().getIdentifier();
  }


  public CurrencyPairDescriptor createValidCurrencyPairDescriptor() {
    return new CurrencyPairDescriptor(
        fixture.getBaseCurrency().getSymbol(),
        fixture.getCounterCurrency().getSymbol());
  }

  public void assertCreateSecurityCalled(ExchangeService service, CurrencyPairDescriptor currencyPairDescriptor) {
    Currency baseCurrency = new Currency(currencyPairDescriptor.getBaseCurrency());
    Currency counterCurrency = new Currency(currencyPairDescriptor.getCounterCurrency());
    CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);
    verify(service, times(1)).registerCurrencyPair(fixture.getExchangeId(), currencyPair);
  }

  protected void assertPlaceOrderCalledOnExchangeService(String broker, String qty, String expectedTicker, Side expectedSide) {
    ArgumentCaptor<ExchangeId> exchangeIdCaptor = ArgumentCaptor.forClass(ExchangeId.class);
    ArgumentCaptor<SecurityOrder> orderCaptor = ArgumentCaptor.forClass(SecurityOrder.class);
    verify(exchangeService, times(1)).placeOrder(exchangeIdCaptor.capture(), orderCaptor.capture());

    assertEquals(fixture.getExchangeId(), exchangeIdCaptor.getValue());

    MarketOrder actualOrder = (MarketOrder) orderCaptor.getValue();
    assertEquals(broker, actualOrder.getBroker());
    assertEquals(expectedSide, actualOrder.getSide());
    assertEquals(qty, actualOrder.getUnfilledQuantity().getRaw());
    assertEquals(expectedTicker, actualOrder.getTicker().getSymbol());
  }
}