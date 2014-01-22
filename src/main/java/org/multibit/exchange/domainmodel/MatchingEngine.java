package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;

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
public class MatchingEngine {
  private Ticker ticker;
  private final OrderBook buyBook;
  private final OrderBook sellBook;
  private EventPublisher eventPublisher;

  public MatchingEngine(Ticker ticker, OrderBook buyBook, OrderBook sellBook, EventPublisher eventPublisher) {
    this.ticker = ticker;
    this.buyBook = buyBook;
    this.sellBook = sellBook;
    this.eventPublisher = eventPublisher;
  }

  public void acceptOrder(SecurityOrder order) {
    Side side = order.getSide();
    OrderBook book = getBook(side);
    OrderBook counterBook = getCounterBook(side);
    Optional<SecurityOrder> securityOrderOptional = tryMatch(order, counterBook);
    if (securityOrderOptional.isPresent()) {
      SecurityOrder securityOrder = securityOrderOptional.get();
      if (securityOrder.isLimitOrder()) {
        book.add(securityOrder);
        eventPublisher.publish(new OrderPlaced(order));
      } else {
        eventPublisher.publish(new OrderCancelled(order));
      }
    }
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
          counterBook.decreaseTopBy(trade.getQuantity());
          eventPublisher.publish(trade);
          SecurityOrder unfilledOrder = order.decreasedBy(trade.getQuantity());
          return tryMatch(unfilledOrder, counterBook);
        }
      }
    }
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
        ItemQuantity quantityTraded = buy.getQuantity().min(sell.getQuantity());
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
