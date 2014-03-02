package org.multibit.exchange.domain.model;

import java.util.Comparator;

/**
 * <p>Utility to provide suitable {@link Comparator}s for each side of an order book.</p>
 *
 * @since 0.0.1
 */
public class OrderBookComparator {
  private static final Comparator<ItemPrice> BUY_COMPARATOR = new BuyBookComparator();
  private static final Comparator<ItemPrice> SELL_COMPARATOR = new SellBookComparator();

  public static Comparator<ItemPrice> forSide(Side side) {
    return (side == Side.BUY) ? BUY_COMPARATOR : SELL_COMPARATOR;
  }

  private static class BuyBookComparator implements Comparator<ItemPrice> {

    @Override
    public int compare(ItemPrice o1, ItemPrice o2) {
      return o2.toBigDecimal().compareTo(o1.toBigDecimal());
    }
  }

  private static class SellBookComparator implements Comparator<ItemPrice> {

    @Override
    public int compare(ItemPrice o1, ItemPrice o2) {
      return o1.toBigDecimal().compareTo(o2.toBigDecimal());
    }
  }
}
