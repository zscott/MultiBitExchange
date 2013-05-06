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
  private SortedSet<SecurityOrder> openBids = Sets.newTreeSet(securityOrderComparator);
  private SortedSet<SecurityOrder> openAsks = Sets.newTreeSet(securityOrderComparator);

  public Optional<Trade> add(SecurityOrder order) throws DuplicateOrderException {
    if (order.getType() == OrderType.BID) {
      if (openBids.contains(order)) {
        throw new DuplicateOrderException(order);
      }
      openBids.add(order);
    } else {
      if (openAsks.contains(order)) {
        throw new DuplicateOrderException(order);
      }
      openAsks.add(order);
    }

    if (bidsAndAsksExist())
      return matchOrders();

    return Optional.absent();
  }

  private boolean bidsAndAsksExist() {
    return !openBids.isEmpty() && !openAsks.isEmpty();
  }

  protected Optional<Trade> matchOrders() {
    SecurityOrder highestBid = getHighestBid();
    SecurityOrder lowestAsk = getLowestAsk();

    if (bidEqualsOrExceedsAsk(highestBid, lowestAsk)) {
      return Optional.of(trade(highestBid, lowestAsk));
    }
    return Optional.absent();
  }

  private Trade trade(SecurityOrder bid, SecurityOrder ask) {
    ItemQuantity quantity = getMaximumTradeableQuantity(bid, ask);
    Trade trade = new Trade(bid, ask, quantity, new ItemPrice("0"));
    bid.addTrade(trade);
    ask.addTrade(trade);

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
    ItemQuantity bidQuantity = bid.getQuantity();
    ItemQuantity askQuantity = ask.getQuantity();
    return (bidQuantity.compareTo(askQuantity) > 0)
      ?askQuantity
      :bidQuantity;
  }

  private boolean bidEqualsOrExceedsAsk(SecurityOrder highestBid, SecurityOrder lowestAsk) {
    return highestBid.isMarket() || lowestAsk.isMarket();
  }

  public SecurityOrder getLowestAsk() {
    return openAsks.first();
  }

  public SecurityOrder getHighestBid() {
    return openBids.first();
  }
}
