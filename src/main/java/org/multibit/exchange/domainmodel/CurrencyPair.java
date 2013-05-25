package org.multibit.exchange.domainmodel;

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
