package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Lists;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.PricedItemComparator;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Set;
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

  @JsonCreator
  public OrderBookReadModel() {
  }

  public OrderBookReadModel(Side side) {
    this.side = side;
    this.book = new TreeMap<>(PricedItemComparator.forSide(side));
  }

  public void addNewPriceLevel(ItemPrice priceLevel, LimitOrder order) {
    LinkedList<LimitOrder> limitOrders = new LinkedList<>();
    limitOrders.addLast(order);
    book.put(priceLevel, limitOrders);
  }

  public void partialFillTopOrderAtPriceLevel(ItemPrice priceLevel, Trade trade) {
    LinkedList<LimitOrder> limitOrders = book.get(priceLevel);
    LimitOrder limitOrder = limitOrders.removeFirst();
    limitOrders.addFirst((LimitOrder) limitOrder.decreasedBy(trade.getQuantity()));
  }

  public void completelyFillTopOrderAtPriceLevel(ItemPrice priceLevel) {
    book.get(priceLevel).removeFirst();
  }

  public void addOrderAtPriceLevel(ItemPrice priceLevel, LimitOrder order) {
    book.get(priceLevel).addLast(order);
  }

  public void removePriceLevel(ItemPrice priceLevel) {
    book.remove(priceLevel);
  }

  public LinkedList<LimitOrder> getOpenOrders(ItemPrice priceLevel) {
    return book.get(priceLevel);
  }

  public BigDecimal getUnfilledQuantity(ItemPrice priceLevel) {
    BigDecimal totalQuantity = BigDecimal.ZERO;
    LinkedList<LimitOrder> openOrders = getOpenOrders();
    for (LimitOrder limitOrder : openOrders) {
      totalQuantity = totalQuantity.add(limitOrder.getUnfilledQuantity().getQuantity());
    }
    return totalQuantity;
  }

  public LinkedList<LimitOrder> getOpenOrders() {
    LinkedList<LimitOrder> allOrders = Lists.newLinkedList();
    for (ItemPrice priceLevel : book.keySet()) {
      allOrders.addAll(book.get(priceLevel));
    }
    return allOrders;
  }

  public Set<ItemPrice> getPriceLevels() {
    return book.keySet();
  }

  public LimitOrder getTop() {
    ItemPrice topPrice = book.firstKey();
    return book.get(topPrice).getFirst();
  }
}
