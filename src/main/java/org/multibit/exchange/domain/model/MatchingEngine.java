package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.MarketOrderAddedEvent;
import org.multibit.exchange.domain.event.OrderCancelledEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;

/**
 * <p>MatchingEngine to provide the following to the core domain:</p>
 * <ul>
 * <li>Keeps track of buy and sell orders submitted for a single ticker/symbol.</li>
 * <li>Supports Market orders</li>
 * <li>Supports Limit orders</li>
 * <li>When the order book becomes crossed the Matching Engine will execute trades automatically.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MatchingEngine extends AbstractAnnotatedEntity {

  private ExchangeId exchangeId;

  private Ticker ticker;

  @EventSourcedMember
  private final OrderBook buyBook;

  @EventSourcedMember
  private final OrderBook sellBook;

  public MatchingEngine(ExchangeId exchangeId, Ticker ticker) {
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.buyBook = new OrderBook(exchangeId, ticker, Side.BUY);
    this.sellBook = new OrderBook(exchangeId, ticker, Side.SELL);
  }

  public void acceptOrder(SecurityOrder originalOrder) {
    Side side = originalOrder.getSide();
    OrderBook counterBook = getCounterBook(side);
    Optional<SecurityOrder> unmatchedOrderOption = tryMatch(originalOrder, counterBook);
    if (unmatchedOrderOption.isPresent()) {
      SecurityOrder unmatchedOrder = unmatchedOrderOption.get();
      if (unmatchedOrder.isMarketOrder()) {
        apply(new OrderCancelledEvent(unmatchedOrder, "No matching orders in counterbook."));
      } else {
        getBook(side).add(unmatchedOrder);
      }
    }
  }

  /**
   * Tries to match an order against the counterBook. Yielding zero or more trades.
   *
   * @param order       The order.
   * @param counterBook The counterBook for the order. i.e., If a sell order, the counterBook is the buy book.
   * @return Unmatched portion of order.
   */
  private Optional<SecurityOrder> tryMatch(SecurityOrder order, OrderBook counterBook) {
    Preconditions.checkArgument(!order.getSide().equals(counterBook.getSide()), "order side and counterBook side must not match");

    if (order.isFilled()) {
      return Optional.absent();
    } else {
      Optional<SecurityOrder> topOrderInCounterBook = counterBook.getTop();
      if (topOrderInCounterBook.isPresent()) {
        SecurityOrder bestCounterOrder = topOrderInCounterBook.get();
        Optional<Trade> tradeOptional = tryToTrade(order, bestCounterOrder);
        if (tradeOptional.isPresent()) {
          Trade trade = tradeOptional.get();
          counterBook.decreaseTopByTradeQuantity(trade);
          SecurityOrder unfilledOrder = order.decreasedBy(trade.getQuantity());
          return tryMatch(unfilledOrder, counterBook);
        } else {
          return Optional.of(order);
        }
      } else {
        return Optional.of(order);
      }
    }
  }

  private Optional<Trade> tryToTrade(SecurityOrder order, SecurityOrder bestCounterOrder) {
    if (bestCounterOrder.isLimitOrder()) {
      LimitOrder bestCounterLimitOrder = (LimitOrder) bestCounterOrder;
      ItemPrice limitPrice = bestCounterLimitOrder.getLimitPrice();
      if (order.crossesAt(limitPrice)) {
        Trade trade = createTrade(order, bestCounterOrder, limitPrice);
        return Optional.of(trade);
      }
    }
    return Optional.absent();
  }

  private Trade createTrade(SecurityOrder order, SecurityOrder counterOrder, ItemPrice limitPrice) {
    SecurityOrder buy;
    SecurityOrder sell;
    if (order.getSide() == Side.BUY) {
      buy = order;
      sell = counterOrder;
    } else {
      buy = counterOrder;
      sell = order;
    }
    ItemQuantity buyQuantity = buy.getUnfilledQuantity();
    ItemQuantity sellQuantity = sell.getUnfilledQuantity();
    ItemQuantity quantityTraded = buyQuantity.min(sellQuantity);
    return new Trade(ticker, buy.getBroker(), sell.getBroker(), limitPrice, quantityTraded);
  }

  @EventHandler
  private void handle(MarketOrderAddedEvent event) {
    getBook(event.getSide()).marketOrderAdded(event.getOrder());
  }

  @EventHandler
  private void handle(LimitOrderAddedToNewPriceLevelEvent event) {
    LimitOrder order = event.getOrder();
    ItemPrice newPriceLevel = event.getPriceLevel();

    getBook(order.getSide()).limitOrderAddedToNewPriceLevel(newPriceLevel, order);
  }

  @EventHandler
  private void handle(LimitOrderAddedToExistingPriceLevelEvent event) {
    LimitOrder order = event.getOrder();
    getBook(order.getSide()).limitOrderAddedToExistingPriceLevel(order);
  }


  @EventHandler
  @SuppressWarnings("unused")
  private void handle(PriceLevelCompletelyFilledEvent event) {
    Side side = event.getSide();
    getBook(side).topPriceLevelFilled();
  }

  @EventHandler
  @SuppressWarnings("unused")
  private void handle(TopOrderPartiallyFilledEvent event) {
    Side side = event.getSide();
    getBook(side).topOrderPartiallyFilled(event.getTrade().getQuantity());
  }


  private OrderBook getCounterBook(Side side) {
    if (side == Side.BUY) return sellBook;
    else return buyBook;
  }

  private OrderBook getBook(Side side) {
    if (side == Side.BUY) return buyBook;
    else return sellBook;
  }
}
