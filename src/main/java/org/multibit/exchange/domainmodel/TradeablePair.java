package org.multibit.exchange.domainmodel;

/**
 * <p>A ValueObject that provides the following to the domain model:</p>
 * <ul>
 * <li>A representation of a pair of {@link TradeableItem}s that can be traded for eachother.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class TradeablePair {

  private final TradeableItem item;
  private final Currency currency;

  public TradeablePair(TradeableItem item, Currency currency) {
    this.item = item;
    this.currency = currency;
  }

  public TradeableItem getItem() {
    return item;
  }

  public Currency getCurrency() {
    return currency;
  }

  public static TradeablePair forSymbols(String tradeableItemSymbol, String currencySymbol) {
    return new TradeablePair(new TradeableItem(tradeableItemSymbol), new Currency(currencySymbol));
  }
}
