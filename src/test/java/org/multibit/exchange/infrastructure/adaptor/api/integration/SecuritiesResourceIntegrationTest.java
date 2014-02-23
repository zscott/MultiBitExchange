package org.multibit.exchange.infrastructure.adaptor.api.integration;

import org.junit.Test;
import org.multibit.exchange.domain.model.ExchangeId;

//fixme - As of Atmosphere 2.0.7 and Dropwizard 0.6.1/0.6.2 this does not work - I get the following error
/*
SEVERE: The following errors and warnings have been detected with resource and/or provider classes:
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
  SEVERE: Missing dependency for field: protected javax.servlet.http.HttpServletRequest org.atmosphere.jersey.AtmosphereFilter.servletReq
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.AtmosphereProviders$BroadcasterProvider.req
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
  SEVERE: Missing dependency for field: javax.servlet.http.HttpServletRequest org.atmosphere.jersey.BaseInjectableProvider.req
 */
public class SecuritiesResourceIntegrationTest { //extends BaseDropWizardResourceIntegrationTest {

  private ExchangeId exchangeId = new ExchangeId("test-exchange");

  @Test
  public void GET_securities() {
//    // Arrange
//    final List<SecurityReadModel> expectedSecurities = Lists.newArrayList();
//    Currency baseCurrency = CurrencyFaker.createValid();
//    Currency counterCurrency = CurrencyFaker.createValid();
//    expectedSecurities.add(new SecurityReadModel(
//        ObjectId.get().toString(),
//        exchangeId.getCode(),
//        TickerFaker.createValid().getSymbol(),
//        baseCurrency.getSymbol(),
//        counterCurrency.getSymbol()));
//    when(readService.fetchSecurities(exchangeId.getCode())).thenReturn(expectedSecurities);
//
//    // Act
//    SecurityListReadModel actual = client()
//        .resource("/exchanges/" + exchangeId.getCode() + "/securities")
//        .get(SecurityListReadModel.class);
//
//    // Assert
//    assertThat(actual.getSecurities()).isEqualTo(expectedSecurities);
//    verify(readService, times(1)).fetchSecurities(exchangeId.getCode());
  }

  @Test
  public void POST_securities() {
//    // Arrange
//    Ticker ticker = TickerFaker.createValid();
//    Currency baseCurrency = CurrencyFaker.createValid();
//    Currency counterCurrency = CurrencyFaker.createValid();
//
//    CurrencyPairDescriptor securityDescriptor =
//        new CurrencyPairDescriptor(
//            ticker.getSymbol(),
//            baseCurrency.getSymbol(),
//            counterCurrency.getSymbol());
//
//    // Act
//    client()
//        .resource("/exchanges/" + exchangeId.getCode() + "/securities")
//        .type(MediaType.APPLICATION_JSON)
//        .post(securityDescriptor);
//
//    // Assert
//    verify(exchangeService, times(1)).registerCurrencyPair(exchangeId, ticker, baseCurrency, counterCurrency);
  }

}
