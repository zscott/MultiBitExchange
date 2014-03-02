package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import com.google.common.collect.Lists;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.OrderBookComparator;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>ReadModel to provide a projection of an OrderBook.</p>
 *
 * @since 0.0.1
 */
public class OrderBookReadModel {

  private SortedMap<ItemPrice, LinkedList<LimitOrder>> book;
  private Side side;

  public OrderBookReadModel(Side side) {
    this.side = side;
    this.book = new TreeMap<>(OrderBookComparator.forSide(side));
  }

  public void addNewPriceLevel(ItemPrice priceLevel, LimitOrder order) {
    LinkedList<LimitOrder> limitOrders = new LinkedList<>();
    limitOrders.addLast(order);
    book.put(priceLevel, limitOrders);
  }

  public void reducePriceLevel(ItemPrice priceLevel, Trade trade) {
    LinkedList<LimitOrder> limitOrders = book.get(priceLevel);
    try {
      LimitOrder limitOrder = limitOrders.removeFirst();
      limitOrders.addFirst((LimitOrder) limitOrder.decreasedBy(trade.getQuantity()));
    } catch (NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  public void increasePriceLevel(ItemPrice priceLevel, LimitOrder order) {
    book.get(priceLevel).addLast(order);
  }

  public void removePriceLevel(ItemPrice priceLevel) {
    book.remove(priceLevel);
  }

  public LinkedList<LimitOrder> getOrdersAtPriceLevel(ItemPrice priceLevel) {
    return book.get(priceLevel);
  }

  public LinkedList<LimitOrder> getOrders() {
    LinkedList<LimitOrder> allOrders = Lists.newLinkedList();
    for (ItemPrice priceLevel : book.keySet()) {
      allOrders.addAll(book.get(priceLevel));
    }
    return allOrders;
  }

  public void removeTopOrder(ItemPrice priceLevel) {
    try {
      book.get(priceLevel).removeFirst();
    } catch (NoSuchElementException e) {
      e.printStackTrace();
    }
  }
}
