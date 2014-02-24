package org.multibit.exchange.infrastructure.adaptor.web.integration;

import org.junit.Test;

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
public class OrdersResourceIntegrationTest { //extends BaseDropWizardResourceIntegrationTest {

  @Test
  public void GET_orderbook() {
//    // Arrange
//    final String exchangeId = "test-exchange";
//    final String orderId = UUID.randomUUID().toString();
//    final String orderType = "BID";
//    final Ticker ticker = TickerFaker.createValid();
//    final BigDecimal orderAmount = new BigDecimal("86.75309");
//    Currency baseCurrency = CurrencyFaker.createValid();
//    Currency counterCurrency = CurrencyFaker.createValid();
//    final Date orderTimestamp = DateUtils.nowUtc().toDate();
//
//    final List<OrderReadModel> expectedOrders = Lists.newArrayList();
//
//    expectedOrders.add(new OrderReadModel(
//        ObjectId.get().toString(),
//        orderId,
//        orderType,
//        ticker.getSymbol(),
//        baseCurrency.getSymbol(),
//        orderAmount,
//        counterCurrency.getSymbol(),
//        orderTimestamp));
//
//    when(readService.fetchOpenOrders(ticker.getSymbol())).thenReturn(expectedOrders);
//
//    // Act
//    OrderListReadModel actual = client()
//        .resource("/exchanges/" + exchangeId + "/securities/" + ticker.getSymbol() + "/orderbook")
//        .get(OrderListReadModel.class);
//
//    // Assert
//    assertThat(actual.getOrders()).isEqualTo(expectedOrders);
//    assertThat(actual.getTicker()).isEqualTo(ticker.getSymbol());
//    verify(readService, times(1)).fetchOpenOrders(ticker.getSymbol());
  }


}
