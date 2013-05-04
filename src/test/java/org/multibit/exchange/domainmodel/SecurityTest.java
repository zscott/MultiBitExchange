package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.testing.OrderAmountFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeablePairFaker;

public class SecurityTest {

  @Test
  public void placeBidOrder() {
    // Arrange
    Ticker ticker = TickerFaker.createValid();
    TradeablePair tradeablePair = TradeablePairFaker.createValid();

    Security security = new Security(ticker, tradeablePair);
    OrderAmount amount = OrderAmountFaker.createValid();
    SecurityOrderId id = SecurityOrderId.next();
    SecurityOrder order = new MarketBidOrder(id, amount);

    // Act
    security.placeOrder(order);

    // Assert

  }


}
