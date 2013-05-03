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
  private final TradeableItem counterItem;

  public TradeablePair(TradeableItem item, TradeableItem pricedIn) {
    this.item = item;
    this.counterItem = pricedIn;
  }

  public TradeableItem getItem() {
    return item;
  }

  public TradeableItem getCounterItem() {
    return counterItem;
  }

  public static TradeablePair forSymbols(String tradeableItemSymbol, String currencySymbol) {
    // fixme - TradeableItems should be looked up rather than create each time.
    return new TradeablePair(new TradeableItem(tradeableItemSymbol), new TradeableItem(currencySymbol));
  }
}
