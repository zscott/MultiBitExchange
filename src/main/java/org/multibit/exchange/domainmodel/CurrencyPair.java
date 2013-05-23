package org.multibit.exchange.domainmodel;

/**
 * <p>A ValueObject that provides the following to the domain model:</p>
 * <ul>
 * <li>A representation of a currency pair.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class CurrencyPair {

  private final Currency baseCurrency;
  private final Currency counterCurrency;

  public CurrencyPair(Currency baseCurrency, Currency counterCurrency) {
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  public Currency getBaseCurrency() {
    return baseCurrency;
  }

  public Currency getCounterCurrency() {
    return counterCurrency;
  }

}
