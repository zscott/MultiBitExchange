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

  private final TradeableItem item;
  private final Currency currency;

  public CurrencyPair(TradeableItem item, Currency currency) {
    this.item = item;
    this.currency = currency;
  }

  public TradeableItem getItem() {
    return item;
  }

  public Currency getCurrency() {
    return currency;
  }

  public static CurrencyPair forSymbols(String tradeableItemSymbol, String currencySymbol) {
    return new CurrencyPair(new TradeableItem(tradeableItemSymbol), new Currency(currencySymbol));
  }
}
