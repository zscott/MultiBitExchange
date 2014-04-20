package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.OrderCancelledEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>An OrderBook representing a single side of an order book.</p>
 *
 * @since 0.0.1
 */
public class OrderBook extends AbstractAnnotatedEntity {

  private final ExchangeId exchangeId;
  private Side side;

  private TreeMap<ItemPrice, LinkedList<LimitOrder>> limitBook;

  public OrderBook(ExchangeId exchangeId, CurrencyPairId currencyPairId, Side side) {
    this.exchangeId = exchangeId;
    Preconditions.checkArgument(side != null, "side must not be null");
    this.side = side;
    limitBook = new TreeMap<>(PricedItemComparator.forSide(this.side));
  }

  public Side getSide() {
    return side;
  }

  public void add(SecurityOrder order) {
    try {
      Preconditions.checkArgument(order.getSide().equals(side), "order side must match orderbook side");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    order.addToOrderBook(this);
  }

  protected void addMarketOrder(MarketOrder order) {
    apply(new OrderCancelledEvent(order, "Unfilled market order cancelled."));
  }

  protected void addLimitOrder(LimitOrder order) {
    ItemPrice limitPrice = order.getLimitPrice();
    if (limitBook.containsKey(limitPrice)) {
      addLimitOrderToExistingPriceLevel(order, limitPrice);
    } else {
      addLimitOrderToNewPriceLevel(order, limitPrice);
    }
  }

  private void addLimitOrderToNewPriceLevel(LimitOrder order, ItemPrice priceLevel) {
    apply(new LimitOrderAddedToNewPriceLevelEvent(exchangeId, order, priceLevel));
  }

  private void addLimitOrderToExistingPriceLevel(LimitOrder order, ItemPrice priceLevel) {
    apply(new LimitOrderAddedToExistingPriceLevelEvent(exchangeId, order, priceLevel));
  }

  public List<SecurityOrder> getOrders() {
    List<SecurityOrder> orders = Lists.newLinkedList();
    for (ItemPrice limit : limitBook.keySet()) {
      orders.addAll(limitBook.get(limit));
    }
    return orders;
  }

  public Optional<SecurityOrder> getTop() {
    if (!limitBook.isEmpty()) {
      List<LimitOrder> topLimitOrders = getTopLimitOrders();
      if (!topLimitOrders.isEmpty()) {
        return Optional.of((SecurityOrder) topLimitOrders.get(0));
      }
    }

    return Optional.absent();
  }

  public void decreaseTopByTradeQuantity(Trade trade) {
    ItemQuantity quantity = trade.getQuantity();
    Preconditions.checkNotNull(quantity, "quantity must not be null");
    Preconditions.checkArgument(!quantity.isZero(), "quantity must be greater than zero");

    if (!limitBook.isEmpty()) {
      decreaseTopOfLimitBookByTradeQuantity(trade);

    } else {
      throw new IllegalStateException("No top order in empty book.");

    }
  }

  private void decreaseTopOfLimitBookByTradeQuantity(Trade trade) {
    ItemQuantity quantityToDecreaseBy = trade.getQuantity();
    ItemPrice topPriceLevel = getTopPriceLevel();
    LinkedList<LimitOrder> topLimitOrders = limitBook.get(topPriceLevel);

    if (!topLimitOrders.isEmpty()) {
      LimitOrder topLimitOrder = topLimitOrders.peek();

      int newUnfilledQuantity = topLimitOrder.getUnfilledQuantity().compareTo(quantityToDecreaseBy);
      Preconditions.checkState(newUnfilledQuantity >= 0, "cannot decrease top of orderbook by more than available quantity");

      boolean orderCompletelyFilled = newUnfilledQuantity == 0;
      boolean priceLevelCompletelyFilled = topLimitOrders.size() == 1;
      if (orderCompletelyFilled) {

        if (priceLevelCompletelyFilled) {
          priceLevelCompletelyFilled(topPriceLevel, trade);

        } else {
          topOrderCompletelyFilled(topPriceLevel, trade);
        }

      } else {
        topOrderPartiallyFilled(topPriceLevel, trade);

      }
    }
  }

  private void topOrderCompletelyFilled(ItemPrice priceLevel, Trade trade) {
    Preconditions.checkArgument(priceLevel.equals(trade.getPrice()), "priceLevel and trade price must match");

    apply(new TopOrderCompletelyFilledEvent(exchangeId, side, priceLevel, trade));
  }

  private ItemPrice getTopPriceLevel() {
    return limitBook.firstKey();
  }

  private LinkedList<LimitOrder> getTopLimitOrders() {
    ItemPrice topPriceLevel = getTopPriceLevel();
    return limitBook.get(topPriceLevel);
  }

  private void priceLevelCompletelyFilled(ItemPrice priceLevel, Trade trade) {
    Preconditions.checkArgument(priceLevel.equals(trade.getPrice()), "priceLevel and trade price must match");

    apply(new PriceLevelCompletelyFilledEvent(exchangeId, side, priceLevel, trade));
  }

  private void topOrderPartiallyFilled(ItemPrice priceLevel, Trade trade) {
    try {
      Preconditions.checkArgument(priceLevel.equals(trade.getPrice()), "priceLevel and trade price must match");
    } catch (IllegalArgumentException e) {
      throw e;
    }

    apply(new TopOrderPartiallyFilledEvent(exchangeId, side, priceLevel, trade));
  }

  public void topPriceLevelFilled() {
    limitBook.remove(getTopPriceLevel());
  }

  public void topOrderPartiallyFilled(ItemQuantity quantity) {
    LinkedList<LimitOrder> topLimitOrders = getTopLimitOrders();
    LimitOrder topLimitOrder = topLimitOrders.removeFirst();
    topLimitOrders.addFirst((LimitOrder) topLimitOrder.decreasedBy(quantity));
  }

  public void limitOrderAddedToNewPriceLevel(ItemPrice newPriceLevel, LimitOrder order) {
    LinkedList<LimitOrder> orders = Lists.newLinkedList();
    orders.add(order);
    limitBook.put(newPriceLevel, orders);
  }

  public void limitOrderAddedToExistingPriceLevel(LimitOrder order) {
    limitBook.get(order.getLimitPrice()).add(order);
  }
}

