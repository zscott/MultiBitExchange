package org.multibit.exchange.domainmodel;

import com.google.common.collect.Lists;
import org.apache.commons.collections.FastTreeMap;

import java.util.List;
import java.util.SortedMap;

/**
 * <p>An OrderBook representing a single side of an order book.</p>
 *
 * @since 0.0.1
 */
public class OrderBook {
  private Side side;
  private List<MarketOrder> marketBook = Lists.newLinkedList();

  private LimitBookItemPriceComparator limitBookItemPriceComparator = new LimitBookItemPriceComparator();
  private SortedMap<ItemPrice, List<LimitOrder>> limitBook = new FastTreeMap(limitBookItemPriceComparator);

  public OrderBook(Side side) {
    this.side = side;
  }


  public void add(SecurityOrder order) {
    order.addToOrderBook(this);
  }

  protected void addMarketOrder(MarketOrder order) {
    marketBook.add(order);
  }

  protected void addLimitOrder(LimitOrder order) {
    ItemPrice limitPrice = order.getLimitPrice();
    if (limitBook.containsKey(limitPrice)) {
      limitBook.get(limitPrice).add(order);
    } else {
      List<LimitOrder> orders = Lists.newLinkedList();
      orders.add(order);
      limitBook.put(limitPrice, orders);
    }
  }

  public List<SecurityOrder> getOrders() {
    List<SecurityOrder> orders = Lists.newLinkedList();
    orders.addAll(marketBook);
    for (ItemPrice limit : limitBook.keySet()) {
      orders.addAll(limitBook.get(limit));
    }
    return orders;
  }
}
