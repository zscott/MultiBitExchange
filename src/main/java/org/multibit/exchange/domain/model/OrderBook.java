package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.collections.FastTreeMap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

/**
 * <p>An OrderBook representing a single side of an order book.</p>
 *
 * @since 0.0.1
 */
public class OrderBook {
  private Side side;
  private LinkedList<MarketOrder> marketBook = Lists.newLinkedList();

  private SortedMap<ItemPrice, LinkedList<LimitOrder>> limitBook;

  public OrderBook(Side side) {
    this.side = side;
    Comparator priceComparator = getPriceComparator();
    limitBook = new FastTreeMap(priceComparator);
  }

  private Comparator getPriceComparator() {
    Comparator priceComparator;
    if (side == Side.BUY) {
      priceComparator = new BuyBookComparator();
    } else {
      priceComparator = new SellBookComparator();
    }
    return priceComparator;
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
      LinkedList<LimitOrder> orders = Lists.newLinkedList();
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

  public Optional<SecurityOrder> getTop() {
    if (!marketBook.isEmpty()) {
      return Optional.of((SecurityOrder)marketBook.get(0));
    }

    if (!limitBook.isEmpty()) {
      ItemPrice topPriceLevel = limitBook.firstKey();
      List<LimitOrder> topLimitOrders = limitBook.get(topPriceLevel);
      if (!topLimitOrders.isEmpty()) {
        return Optional.of((SecurityOrder)topLimitOrders.get(0));
      }
    }

    return Optional.absent();
  }

  public void decreaseTopBy(ItemQuantity quantity) {
    // todo: quantity should always be less than or equal to the top order's quantity. Add a precondition to check this.
    if (!marketBook.isEmpty()) {
      MarketOrder top = marketBook.remove(0);
      if (!quantity.equals(top.getQuantity())) {
        marketBook.addFirst((MarketOrder) top.decreasedBy(quantity));
      }
    } else if (!limitBook.isEmpty()) {
      ItemPrice topPriceLevel = limitBook.firstKey();
      LinkedList<LimitOrder> topLimitOrders = limitBook.get(topPriceLevel);
      if (!topLimitOrders.isEmpty()) {
        LimitOrder top = topLimitOrders.remove(0);
        boolean sameSize = top.getQuantity().equals(quantity);
        if (sameSize) {
          if (topLimitOrders.size() == 0) {
            limitBook.remove(topPriceLevel);
          }
        } else {
          topLimitOrders.addFirst((LimitOrder) top.decreasedBy(quantity));
        }
      }
    } else {
      throw new IllegalStateException("No top order in empty book.");
    }
  }

  private class BuyBookComparator implements Comparator<ItemPrice> {

    @Override
    public int compare(ItemPrice o1, ItemPrice o2) {
      return o2.toBigDecimal().compareTo(o1.toBigDecimal());
    }
  }

  private class SellBookComparator implements Comparator<ItemPrice> {

    @Override
    public int compare(ItemPrice o1, ItemPrice o2) {
      return o1.toBigDecimal().compareTo(o2.toBigDecimal());
    }
  }

}

