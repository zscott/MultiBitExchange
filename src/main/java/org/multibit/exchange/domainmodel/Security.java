package org.multibit.exchange.domainmodel;

/**
 * <p>Domain object that provides the following to the domain model:</p>
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
 * @since 0.0.1
 *         
 */
public class Security {

  /**
   * Used to identify and display this Security.
   */
  private final Ticker ticker;

  /**
   * The pair of {@link org.multibit.exchange.domainmodel.TradeableItem}s that can be traded in this security.
   */
  private TradeablePair tradeablePair;

  private final OrderBook orderbook = new OrderBook();

  public Security(Ticker ticker, TradeablePair tradeablePair) {
    this.ticker = ticker;
    this.tradeablePair = tradeablePair;
  }

  @Override
  public String toString() {
    return "Security{" +
        "ticker='" + ticker + '\'' +
        ", tradeablePair=" + tradeablePair +
        '}';
  }

  public void placeOrder(SecurityOrder order) {
    orderbook.add(order);
  }
}
