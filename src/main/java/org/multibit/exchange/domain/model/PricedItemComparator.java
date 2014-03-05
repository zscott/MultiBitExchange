package org.multibit.exchange.domain.model;

import java.util.Comparator;

/**
 * <p>Utility to provide suitable {@link Comparator}s for each side of an order book.</p>
 *
 * @since 0.0.1
 */
public class PricedItemComparator {
  private static final Comparator<PricedItem> BUY_COMPARATOR = new BuyBookComparator();
  private static final Comparator<PricedItem> SELL_COMPARATOR = new SellBookComparator();

  public static Comparator<PricedItem> forSide(Side side) {
    return (side == Side.BUY) ? BUY_COMPARATOR : SELL_COMPARATOR;
  }

  private static class BuyBookComparator implements Comparator<PricedItem> {

    @Override
    public int compare(PricedItem o1, PricedItem o2) {
      return o2.getBigDecimalPrice().compareTo(o1.getBigDecimalPrice());
    }
  }

  private static class SellBookComparator implements Comparator<PricedItem> {

    @Override
    public int compare(PricedItem o1, PricedItem o2) {
      return o1.getBigDecimalPrice().compareTo(o2.getBigDecimalPrice());
    }
  }
}
