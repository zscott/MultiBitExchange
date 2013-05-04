package org.multibit.exchange.domainmodel;

import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

/**
 * <p>Fixture to provide the following to tests:</p>
 * <ul>
 * <li>Encapsulates creation of related objects for testing</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class ExchangeTestFixture {

  private ExchangeId exchangeId = ExchangeIdFaker.createValid();
  private Ticker ticker = TickerFaker.createValid();
  private TradeableItem tradeableItem = TradeableItemFaker.createValid();
  private Currency currency = CurrencyFaker.createValid();


  public ExchangeTestFixture() {

  }

  public Ticker getTicker() {
    return TickerFaker.createValid();
  }

  public Currency getCurrency() {
    return CurrencyFaker.createValid();
  }

  public TradeableItem getTradeableItem() {
    return TradeableItemFaker.createValid();
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

}
