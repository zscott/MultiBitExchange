package org.multibit.exchange.domainmodel;

/**
 * <p>A trade.</p>
 *
 * @since 0.0.1
 *         
 */
public class Trade {

  private final SecurityOrder highestBid;
  private final SecurityOrder lowestAsk;

  public Trade(SecurityOrder highestBid, SecurityOrder lowestAsk) {
    this.highestBid = highestBid;
    this.lowestAsk = lowestAsk;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Trade trade = (Trade) o;

    if (!highestBid.equals(trade.highestBid)) return false;
    if (!lowestAsk.equals(trade.lowestAsk)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = highestBid.hashCode();
    result = 31 * result + lowestAsk.hashCode();
    return result;
  }
}
