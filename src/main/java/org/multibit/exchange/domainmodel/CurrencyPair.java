package org.multibit.exchange.domainmodel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>A ValueObject that provides the following to the domain model:</p>
 * <ul>
 * <li>Representation of a currency pair.</li>
 * </ul>
 * <p/>
 * A currency pair is the quotation of the relative value of a currency unit against the
 * unit of another currency in a currency exchange market.
 *
 * @since 0.0.1
 *         
 */
public class CurrencyPair {

  /*
   * The base currency is the currency that is quoted in relation to the counterCurrency. It is also
   * referred to as the transaction currency.
   *
   * The term base currency is also used as the accounting currency by banks, and is usually the domestic currency.
   */
  private final Currency baseCurrency;

  /*
   * The currency that is used as the reference is called the counter currency or quote currency.
   */
  private final Currency counterCurrency;

  private String symbol;

  public CurrencyPair(Currency baseCurrency, Currency counterCurrency) {
    checkNotNull(baseCurrency, "baseCurrency must not be null");
    checkNotNull(counterCurrency, "counterCurrency must not be null");

    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.symbol = baseCurrency.getSymbol() + "-" + counterCurrency.getSymbol();
  }

  public Currency getBaseCurrency() {
    return baseCurrency;
  }

  public Currency getCounterCurrency() {
    return counterCurrency;
  }

  public Ticker getTicker() {
    return new Ticker(getSymbol());
  }

  private String getSymbol() {
    return symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrencyPair that = (CurrencyPair) o;

    if (!baseCurrency.equals(that.baseCurrency)) return false;
    if (!counterCurrency.equals(that.counterCurrency)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = baseCurrency.hashCode();
    result = 31 * result + counterCurrency.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CurrencyPair{" +
      "symbol='" + symbol + '\'' +
      '}';
  }
}
