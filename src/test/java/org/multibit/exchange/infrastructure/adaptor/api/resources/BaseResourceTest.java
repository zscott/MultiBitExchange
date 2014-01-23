package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.testing.ItemQuantityFaker;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * A base test class for testing resources against a simulated container
 */
public abstract class BaseResourceTest {

  protected ExchangeService exchangeService = mock(ExchangeService.class);
  protected ReadService readService = mock(ReadService.class);

  protected SecuritiesResource securitiesResource = new SecuritiesResource(exchangeService, readService);
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
    return fixture.getExchangeId().getName();
  }


  public SecurityDescriptor createValidSecurityDescriptor() {
    return new SecurityDescriptor(
        fixture.getTicker().getSymbol(),
        fixture.getBaseCurrency().getSymbol(),
        fixture.getCounterCurrency().getSymbol());
  }

  public void assertCreateSecurityCalled(ExchangeService service, SecurityDescriptor securityDescriptor) {
    Ticker ticker = new Ticker(securityDescriptor.getTicker());
    Currency baseCurrency = new Currency(securityDescriptor.getBaseCurrency());
    Currency counterCurrency = new Currency(securityDescriptor.getCounterCurrency());
    verify(service, times(1)).createSecurity(fixture.getExchangeId(), ticker, baseCurrency, counterCurrency);
  }
}