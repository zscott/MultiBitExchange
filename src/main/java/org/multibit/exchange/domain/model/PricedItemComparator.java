package org.multibit.exchange.domain.model;

import java.util.Comparator;

/**
 * <p>Utility to provide suitable {@link Comparator}s for each side of an order book.</p>
 *
 * @since 0.0.1
 */
public class PricedItemComparator {
  private static final Comparator<PricedItem> BUY_SIDE_COMPARATOR = new BuySideComparator();
  private static final Comparator<PricedItem> SELL_SIDE_COMPARATOR = new SellSideComparator();

  public static Comparator<PricedItem> forSide(Side side) {
    return (side == Side.BUY) ? BUY_SIDE_COMPARATOR : SELL_SIDE_COMPARATOR;
  }
}
