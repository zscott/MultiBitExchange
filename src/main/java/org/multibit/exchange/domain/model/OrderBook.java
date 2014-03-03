package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.MarketOrderAddedEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;

import java.util.Comparator;
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
  private final Ticker ticker;
  private Side side;

  private LinkedList<MarketOrder> marketBook = Lists.newLinkedList();

  private TreeMap<ItemPrice, LinkedList<LimitOrder>> limitBook;

  public OrderBook(ExchangeId exchangeId, Ticker ticker, Side side) {
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    Preconditions.checkArgument(side != null, "side must not be null");
    this.side = side;
    Comparator<ItemPrice> priceComparator = getPriceComparator();
    limitBook = new TreeMap<>(priceComparator);
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
    apply(new MarketOrderAddedEvent(exchangeId, ticker, side, order));
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
    orders.addAll(marketBook);
    for (ItemPrice limit : limitBook.keySet()) {
      orders.addAll(limitBook.get(limit));
    }
    return orders;
  }

  public Optional<SecurityOrder> getTop() {
    if (!marketBook.isEmpty()) {
      return Optional.of((SecurityOrder) marketBook.get(0));
    }

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

    if (!marketBook.isEmpty()) {
      decreaseTopOfMarketBookByTradeQuantity(trade);

    } else if (!limitBook.isEmpty()) {
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

  // fixme - This should never happen because Market Orders don't sit on the book.
  private void decreaseTopOfMarketBookByTradeQuantity(Trade trade) {
    ItemQuantity decreaseByQuantity = trade.getQuantity();
    MarketOrder top = marketBook.peek();
    if (!decreaseByQuantity.equals(top.getUnfilledQuantity())) {
      marketBook.removeFirst();
      marketBook.addFirst((MarketOrder) top.decreasedBy(decreaseByQuantity));
    } else {
      marketBook.removeFirst();
    }
  }

  private Comparator<ItemPrice> getPriceComparator() {
    return OrderBookComparator.forSide(side);
  }

  public void topPriceLevelFilled() {
    limitBook.remove(getTopPriceLevel());
  }

  public void topOrderPartiallyFilled(ItemQuantity quantity) {
    LinkedList<LimitOrder> topLimitOrders = getTopLimitOrders();
    LimitOrder topLimitOrder = topLimitOrders.removeFirst();
    topLimitOrders.addFirst((LimitOrder) topLimitOrder.decreasedBy(quantity));
  }

  public void marketOrderAdded(MarketOrder order) {
    marketBook.add(order);
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

