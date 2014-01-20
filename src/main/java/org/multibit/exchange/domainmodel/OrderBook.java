package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.SortedSet;

/**
 * <p>An order book.</p>
 *
 * @since 0.0.1
 *         
 */
public class OrderBook {
  private final SecurityOrderComparator securityOrderComparator = new SecurityOrderComparator();
  private final CurrencyPair pair;
  private SortedSet<BuyOrder> openBids = Sets.newTreeSet(securityOrderComparator);
  private SortedSet<SellOrder> openAsks = Sets.newTreeSet(securityOrderComparator);

  public OrderBook(CurrencyPair pair) {
    this.pair = pair;
  }

  public Optional<Trade> addOrderAndExecuteTrade(SecurityOrder order) throws DuplicateOrderException {
    order.addToOrderbook(this);
    return executeTradeIfPossible();
  }

  void addBuyOrder(BuyOrder order) throws DuplicateOrderException {
    if (openBids.contains(order)) {
      throw new DuplicateOrderException(order);
    }
    openBids.add(order);
  }

  void addSellOrder(SellOrder order) throws DuplicateOrderException {
    if (openAsks.contains(order)) {
      throw new DuplicateOrderException(order);
    }
    openAsks.add(order);
  }

  public Optional<Trade> executeTradeIfPossible() {
    if (bidsAndAsksExist())
      return matchOrders();
    else
      return Optional.absent();
  }

  private boolean bidsAndAsksExist() {
    return !openBids.isEmpty() && !openAsks.isEmpty();
  }

  protected Optional<Trade> matchOrders() {
    BuyOrder highestBid = getHighestBid();
    SellOrder lowestAsk = getLowestAsk();

    if (bidEqualsOrExceedsAsk(highestBid, lowestAsk)) {
      return Optional.of(trade(highestBid, lowestAsk));
    }
    return Optional.absent();
  }

  private Trade trade(BuyOrder bid, SellOrder ask) {
    ItemQuantity quantity = getMaximumTradeableQuantity(bid, ask);
    Trade trade = new Trade(bid, ask, quantity, new ItemPrice("0"));
    bid.recordTrade(trade);
    ask.recordTrade(trade);

    removeFilledOrders(bid, ask);

    return trade;
  }

  private void removeFilledOrders(SecurityOrder bid, SecurityOrder ask) {
    if (bid.isFilled())
      openBids.remove(bid);

    if (ask.isFilled())
      openAsks.remove(ask);
  }

  private ItemQuantity getMaximumTradeableQuantity(SecurityOrder bid, SecurityOrder ask) {
    ItemQuantity bidQuantity = bid.getUnfilledQuantity();
    ItemQuantity askQuantity = ask.getUnfilledQuantity();
    return (bidQuantity.compareTo(askQuantity) > 0)
        ? askQuantity
        : bidQuantity;
  }

  private boolean bidEqualsOrExceedsAsk(SecurityOrder highestBid, SecurityOrder lowestAsk) {
    return highestBid.isMarket() || lowestAsk.isMarket();
  }

  public SellOrder getLowestAsk() {
    return openAsks.first();
  }

  public BuyOrder getHighestBid() {
    return openBids.first();
  }

}
