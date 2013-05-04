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
  private final PriceComparator priceComparator = new PriceComparator();
  private SortedSet<SecurityOrder> bids = Sets.newTreeSet(priceComparator);
  private SortedSet<SecurityOrder> asks = Sets.newTreeSet(priceComparator);

  public Optional<Trade> add(SecurityOrder order) {
    if (order.getType() == OrderType.BID) {
      bids.add(order);
    } else {
      asks.add(order);
    }

    if (bidsAndAsksExist())
      return matchOrders();

    return Optional.absent();
  }

  private boolean bidsAndAsksExist() {
    return !bids.isEmpty() && !asks.isEmpty();
  }

  protected Optional<Trade> matchOrders() {
    SecurityOrder highestBid = getHighestBid();
    SecurityOrder lowestAsk = getLowestAsk();

    if (bidEqualsOrExceedsAsk(highestBid, lowestAsk)) {
      return trade(highestBid, lowestAsk);
    }
    return Optional.absent();
  }

  private Optional<Trade> trade(SecurityOrder highestBid, SecurityOrder lowestAsk) {
    bids.remove(highestBid);
    asks.remove(lowestAsk);

    return Optional.of(new Trade(highestBid, lowestAsk));
  }

  private boolean bidEqualsOrExceedsAsk(SecurityOrder highestBid, SecurityOrder lowestAsk) {
    return priceComparator.compare(lowestAsk, highestBid) <= 0;
  }

  private SecurityOrder getLowestAsk() {
    return asks.first();
  }

  public SecurityOrder getHighestBid() {
    return bids.last();
  }
}
