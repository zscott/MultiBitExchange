package org.multibit.exchange.domainmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

public class ExchangeTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void Create() {
    // Arrange

    // Act
    new Exchange();

    // Assert

  }

  @Test
  public void addSecurity() throws DuplicateTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();
    TradeableItem item = TradeableItemFaker.createValid();
    Currency currency = CurrencyFaker.createValid();
    TradeablePair tradeablePair = new TradeablePair(item, currency);

    // Act
    exchange.addSecurity(ticker, tradeablePair);

    // Assert

  }

  @Test
  public void addDuplicateSecurity() throws DuplicateTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();
    TradeableItem item = TradeableItemFaker.createValid();
    Currency currency = CurrencyFaker.createValid();
    TradeablePair tradeablePair = new TradeablePair(item, currency);

    exchange.addSecurity(ticker, tradeablePair);
    thrown.expect(DuplicateTickerException.class);
    thrown.expectMessage("ticker " + ticker.getSymbol() + " already exists");

    // Act
    exchange.addSecurity(ticker, tradeablePair);
  }

}
