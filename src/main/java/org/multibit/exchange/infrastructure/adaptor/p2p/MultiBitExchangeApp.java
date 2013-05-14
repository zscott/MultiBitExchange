package org.multibit.exchange.infrastructure.adaptor.p2p;

import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Trade;
import rice.p2p.commonapi.DeliveryNotification;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;

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
public class MultiBitExchangeApp extends AbstractPastryApplication implements DeliveryNotification {

  private static final String INSTANCE = "mbexchange";
  private Node node;
  private final TradeHandler tradeHandler;
  private final OrderHandler orderHandler;
  protected Endpoint endpoint;

  public MultiBitExchangeApp(Node node, TradeHandler tradeHandler, OrderHandler orderHandler) {
    this.node = node;
    this.tradeHandler = tradeHandler;
    this.orderHandler = orderHandler;
    this.endpoint = node.buildEndpoint(this, INSTANCE);

    this.endpoint.register();
    System.out.println("MultiBitExchangeApp initialized...");
  }

  /**
   * Called to route a message to the destinationId
   */
  public void routeOrder(Id destinationId, SecurityOrder order) {
    Message msg = new OrderMessage(endpoint.getId(), destinationId, order);
    routeMessageWithAck(destinationId, msg);
  }

  /**
   * Called to route a message to the destinationId
   */
  public void routeTrade(Id destinationId, Trade trade) {
    Message msg = new TradeMessage(endpoint.getId(), destinationId, trade);
    routeMessageWithAck(destinationId, msg);
  }

  protected void routeMessageWithAck(Id destinationId, Message msg) {
    endpoint.route(destinationId, msg, null, this);
  }

  @Override
  public void deliver(Id id, Message message) {
    if (message instanceof TradeMessage) {
      tradeHandler.handleTrade(((TradeMessage) message).getTrade());
    }

    if (message instanceof OrderMessage) {
      orderHandler.handleOrder(((OrderMessage) message).getOrder());
    }

  }

  @Override
  public String toString() {
    return "MultiBitExchangeApp{" +
        "node=" + node.getId() +
        ", endpoint=" + endpoint.getId() +
        '}';
  }

}
