package org.multibit.exchange.domainmodel;

import org.junit.Test;

import static org.multibit.common.DateUtils.nowUtc;

public class QuoteTest {

  @Test
  public void testSpread() throws DuplicateTickerException, DuplicateOrderException, NoSuchTickerException {
    // Arrange
    Currency baseCurrency = new Currency("BTC");
    Currency counterCurrency = new Currency("CAD");
    CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);
    Exchange ex = new Exchange();
    ex.addSecurity(currencyPair.getTicker(), currencyPair);

    BuyOrder buyOrder = new BuyOrder(SecurityOrderId.next(), OrderType.marketOrder(), currencyPair, new ItemQuantity("10"), nowUtc());
    SellOrder sellOrder = new SellOrder(SecurityOrderId.next(), OrderType.marketOrder(), currencyPair, new ItemQuantity("10"), nowUtc());

    ex.placeOrder(buyOrder);
    ex.placeOrder(sellOrder);

    // Act
    //CurrencyQuote quote = new CurrencyQuote(currencyPair, bestBid, bestAsk);

    // Assert
    //assertThat(actual).isEqualTo(expected);
    //verify(mock, times(1)).call();
  }
}
