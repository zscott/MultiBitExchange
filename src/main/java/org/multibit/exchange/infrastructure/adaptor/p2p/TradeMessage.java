package org.multibit.exchange.infrastructure.adaptor.p2p;

import org.multibit.exchange.domainmodel.Trade;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class TradeMessage implements Message {

  protected Id from;

  protected Id to;

  private final Trade trade;

  public TradeMessage(Id from, Id to, Trade trade) {
    this.from = from;
    this.to = to;
    this.trade = trade;
  }

  @Override
  public int getPriority() {
    return HIGH_PRIORITY;
  }

  public Trade getTrade() {
    return trade;
  }

  @Override
  public String toString() {
    return "TradeMessage{" +
        "trade='" + trade + '\'' +
        '}';
  }
}
