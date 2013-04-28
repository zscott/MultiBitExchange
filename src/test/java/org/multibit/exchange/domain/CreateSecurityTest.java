package org.multibit.exchange.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CreateSecurityTest {

  @Before
  public void setUp() {
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreateSecurity() {
    // Arrange
    String expectedTickerSymbol = "LTC/BTC";
    TradeableItem tradeableItem = new TradeableItem("LTC");
    TradeableItem currencyItem = new TradeableItem("BTC");
    TradeablePair expectedTradeablePair = new TradeablePair(tradeableItem, currencyItem);

    // Act
    new Security(expectedTickerSymbol, expectedTradeablePair);
  }
}
