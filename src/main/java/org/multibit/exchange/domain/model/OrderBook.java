package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>An OrderBook representing a single side of an order book.</p>
 *
 * @since 0.0.1
 */
public class OrderBook {

  private Side side;

  private LinkedList<MarketOrder> marketBook = Lists.newLinkedList();

  private TreeMap<ItemPrice, LinkedList<LimitOrder>> limitBook;

  public OrderBook(Side side) {
    Preconditions.checkArgument(side != null, "side must not be null");
    this.side = side;
    Comparator<ItemPrice> priceComparator = getPriceComparator();
    limitBook = new TreeMap<>(priceComparator);
  }

  private Comparator<ItemPrice> getPriceComparator() {
    Comparator<ItemPrice> priceComparator;
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

  public void decreaseTopBy(ItemQuantity decreaseByQuantity) {
    Preconditions.checkNotNull(decreaseByQuantity, "quantity must not be null");
    Preconditions.checkArgument(!decreaseByQuantity.isZero(), "quantity must be greater than zero");
    if (!marketBook.isEmpty()) {
      MarketOrder top = marketBook.remove(0);
      if (!decreaseByQuantity.equals(top.getQuantity())) {
        marketBook.addFirst((MarketOrder) top.decreasedBy(decreaseByQuantity));
      }
    } else if (!limitBook.isEmpty()) {
      ItemPrice topPriceLevel = limitBook.firstKey();
      LinkedList<LimitOrder> topLimitOrders = limitBook.get(topPriceLevel);
      if (!topLimitOrders.isEmpty()) {
        LimitOrder top = topLimitOrders.remove(0);
        int compareQty = top.getQuantity().compareTo(decreaseByQuantity);
        if (compareQty == 0) {
          if (topLimitOrders.size() == 0) {
            limitBook.remove(topPriceLevel);
          }
        } else if (compareQty > 0) {
          topLimitOrders.addFirst((LimitOrder) top.decreasedBy(decreaseByQuantity));
        } else {
          throw new IllegalArgumentException("cannot decrease top of orderbook by more than available quantity");
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

