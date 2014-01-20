package org.multibit.exchange.domainmodel;

import java.util.Comparator;

/**
 * <p>Comparator to provide the following to {@link [OrderBook]}:</p>
 * <ul>
 * <li>Ability to compare two ItemPrices to keep the limit book in order.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class LimitBookItemPriceComparator implements Comparator<ItemPrice> {

  @Override
  public int compare(ItemPrice o1, ItemPrice o2) {
    return o2.toBigDecimal().compareTo(o1.toBigDecimal());
  }
}
