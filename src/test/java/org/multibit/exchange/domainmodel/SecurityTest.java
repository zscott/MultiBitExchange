package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.testing.OrderAmountFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeablePairFaker;

public class SecurityTest {

  @Test
  public void placeBidOrder() throws DuplicateOrderException {
    // Arrange
    Ticker ticker = TickerFaker.createValid();
    CurrencyPair currencyPair = TradeablePairFaker.createValid();

    Security security = new Security(ticker, currencyPair);
    ItemQuantity amount = OrderAmountFaker.createValid();
    SecurityOrderId id = SecurityOrderId.next();
    SecurityOrder order = new MarketBidOrder(id, amount, org.multibit.common.DateUtils.nowUtc());

    // Act
    security.placeOrder(order);

    // Assert

  }


}
