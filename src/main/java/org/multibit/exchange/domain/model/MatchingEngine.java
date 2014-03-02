package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.multibit.exchange.domain.event.OrderAcceptedEvent;
import org.multibit.exchange.domain.event.OrderCancelledEvent;
import org.multibit.exchange.domain.event.TradeExecutedEvent;

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

  private final OrderBook buyBook;

  private final OrderBook sellBook;

  public MatchingEngine(ExchangeId exchangeId, Ticker ticker, OrderBook buyBook, OrderBook sellBook) {
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.buyBook = buyBook;
    this.sellBook = sellBook;
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
        apply(new OrderAcceptedEvent(unmatchedOrder));
      }
    }
  }

  @EventHandler
  @SuppressWarnings("unused")
  protected void onOrderAddedToBook(OrderAcceptedEvent event) {
    SecurityOrder order = event.getOrder();
    Side side = order.getSide();
    OrderBook book = getBook(side);

    book.add(order);
  }

  private Optional<SecurityOrder> tryMatch(SecurityOrder order, OrderBook counterBook) {
    if (order.getQuantity().isZero()) {
      return Optional.absent();
    } else {
      Optional<SecurityOrder> topOfCounterBookOptional = counterBook.getTop();
      if (!topOfCounterBookOptional.isPresent()) {
        return Optional.of(order);
      } else {
        SecurityOrder topOfCounterBook = topOfCounterBookOptional.get();
        Optional<Trade> tradeOptional = tryMatchWithTop(order, topOfCounterBook);
        if (!tradeOptional.isPresent()) {
          return Optional.of(order);
        } else {
          Trade trade = tradeOptional.get();
          apply(new TradeExecutedEvent(exchangeId, trade, order.getSide()));
          SecurityOrder unfilledOrder = order.decreasedBy(trade.getQuantity());
          return tryMatch(unfilledOrder, counterBook);
        }
      }
    }
  }

  @EventHandler
  @SuppressWarnings("unused")
  protected void onTradeExecuted(TradeExecutedEvent event) {
    Trade trade = event.getTrade();
    ItemQuantity tradeQuantity = trade.getQuantity();
    Side triggeringSide = event.getTriggeringSide();
    OrderBook counterBook = getCounterBook(triggeringSide);

    counterBook.decreaseTopBy(tradeQuantity);
  }

  private Optional<Trade> tryMatchWithTop(SecurityOrder order, SecurityOrder topOfCounterBook) {
    if (topOfCounterBook.isLimitOrder()) {
      LimitOrder counterBookTopLimitOrder = (LimitOrder) topOfCounterBook;
      ItemPrice limitPrice = counterBookTopLimitOrder.getLimitPrice();
      if (order.crossesAt(limitPrice)) {
        SecurityOrder buy;
        SecurityOrder sell;
        if (order.getSide() == Side.BUY) {
          buy = order;
          sell = topOfCounterBook;
        } else {
          buy = topOfCounterBook;
          sell = order;
        }
        ItemQuantity buyQuantity = buy.getQuantity();
        ItemQuantity sellQuantity = sell.getQuantity();
        ItemQuantity quantityTraded = buyQuantity.min(sellQuantity);
        return Optional.of(new Trade(ticker, buy.getBroker(), sell.getBroker(), limitPrice, quantityTraded));
      }
    }
    return Optional.absent();
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
