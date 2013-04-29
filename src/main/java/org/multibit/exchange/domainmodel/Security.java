package org.multibit.exchange.domainmodel;

import java.io.Serializable;

/**
 * <p>Represents a security. Encapsulates and provides a name for a {@link TradeablePair}.</p>
 *
 * @since 0.0.1
 *         
 */
public class Security implements Serializable {

  /**
   * The name of this security.
   */
  private final String tickerSymbol;

  /**
   * The pair of {@link org.multibit.exchange.domainmodel.TradeableItem}s that can be traded in this security.
   */
  private TradeablePair tradeablePair;


  /**
   * <p>AggregateRoot that provides the following to the domain model:</p>
   * <ul>
   * <li>Support for trading one {@link org.multibit.exchange.domainmodel.TradeableItem} for
   * another {@link org.multibit.exchange.domainmodel.TradeableItem} through the
   * addition and removal of {@link Order}s</li>
   * <li>An aggregate containing an {@link OrderBook} which contains {@link Order}s</li>
   * </ul>
   * <p/>
   * <p>
   * The Security does not maintain any history. It matches Bids and Asks and emits events such as
   * {@link OrderAddedEvent}, {@link OrderRemovedEvent}, {@link OrderExecuted} which can be used
   * by {@link ReadModelBuilder}s to maintain any type of persistent read model.
   * </p>
   *
   * @param tickerSymbol  The ticker symbole for this security.
   * @param tradeablePair The pair of {@link TradeableItem}s being traded in this security.
   * @since 0.0.1
   *         
   */
  public Security(String tickerSymbol, TradeablePair tradeablePair) {

    this.tickerSymbol = tickerSymbol;
    this.tradeablePair = tradeablePair;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public TradeablePair getTradeablePair() {
    return tradeablePair;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Security security = (Security) o;

    if (!tickerSymbol.equals(security.tickerSymbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return tickerSymbol.hashCode();
  }

  @Override
  public String toString() {
    return "Security{" +
        "tickerSymbol='" + tickerSymbol + '\'' +
        ", tradeablePair=" + tradeablePair +
        '}';
  }
}
