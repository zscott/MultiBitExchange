package org.multibit.exchange.domainmodel;

import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.TickerFaker;

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
  private Currency baseCurrency = CurrencyFaker.createValid();
  private Currency counterCurrency = CurrencyFaker.createValid();


  public ExchangeTestFixture() {

  }

  public Ticker getTicker() {
    return ticker;
  }

  public Currency getCounterCurrency() {
    return counterCurrency;
  }

  public Currency getBaseCurrency() {
    return baseCurrency;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

}
