package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

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

  private CurrencyPairId currencyPairId;
  private final CurrencyId baseCurrencyId;
  private final CurrencyId counterCurrencyId;

  @EventSourcedMember
  private final OrderBook buyBook;

  @EventSourcedMember
  private final OrderBook sellBook;

  public MatchingEngine(ExchangeId exchangeId, CurrencyPairId currencyPairId, CurrencyId baseCurrencyId, CurrencyId counterCurrencyId) {
    this.exchangeId = exchangeId;
    this.currencyPairId = currencyPairId;
    this.baseCurrencyId = baseCurrencyId;
    this.counterCurrencyId = counterCurrencyId;
    this.buyBook = new OrderBook(exchangeId, currencyPairId, Side.BUY);
    this.sellBook = new OrderBook(exchangeId, currencyPairId, Side.SELL);
  }

  public void acceptOrder(Order originalOrder) {
    Side side = originalOrder.getSide();
    OrderBook counterBook = getCounterBook(side);
    Optional<Order> unmatchedOrderOption = tryMatch(originalOrder, counterBook);
    if (unmatchedOrderOption.isPresent()) {
      Order unmatchedOrder = unmatchedOrderOption.get();
      if (unmatchedOrder.isMarketOrder()) {
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
  private Optional<Order> tryMatch(Order order, OrderBook counterBook) {
    Preconditions.checkArgument(!order.getSide().equals(counterBook.getSide()), "order side and counterBook side must not match");

    if (order.isFilled()) {
      return Optional.absent();
    } else {
      Optional<Order> topOrderInCounterBook = counterBook.getTop();
      if (topOrderInCounterBook.isPresent()) {
        Order bestCounterOrder = topOrderInCounterBook.get();
        Optional<Trade> tradeOptional = tryToTrade(order, bestCounterOrder);
        if (tradeOptional.isPresent()) {
          Trade trade = tradeOptional.get();
          counterBook.decreaseTopByTradeQuantity(trade);
          Order unfilledOrder = order.decreasedBy(trade.getQuantity());
          return tryMatch(unfilledOrder, counterBook);
        } else {
          return Optional.of(order);
        }
      } else {
        return Optional.of(order);
      }
    }
  }

  private Optional<Trade> tryToTrade(Order order, Order bestCounterOrder) {
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

  private Trade createTrade(Order order, Order counterOrder, ItemPrice limitPrice) {
    Order buy;
    Order sell;
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
    return new Trade(currencyPairId, buy.getBroker(), sell.getBroker(), limitPrice, quantityTraded);
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
