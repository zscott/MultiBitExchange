package org.multibit.exchange.domainmodel;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public class MatchingEngine {
  private final OrderBook buyBook;
  private final OrderBook sellBook;

  public MatchingEngine(OrderBook buyBook, OrderBook sellBook) {
    this.buyBook = buyBook;
    this.sellBook = sellBook;
  }

  public void acceptOrder(SecurityOrder order) {

  }
}
