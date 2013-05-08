package org.multibit.exchange.infrastructure.adaptor.p2p;

import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;

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
public class PingApp implements Application {

  private Node node;

  protected Endpoint endpoint;

  public PingApp(Node node) {
    this.node = node;

    this.endpoint = node.buildEndpoint(this, "pingapp");
    this.endpoint.register();

  }

  /**
   * Called to route a message to the destinationId
   */
  public void routeMyMsg(Id destinationId) {
    Message msg = new PingMessage(endpoint.getId(), destinationId);
    System.out.println(this + " sending ping message " + msg + " to " + destinationId);
    endpoint.route(destinationId, msg, null);
  }

  /**
   * Called to directly send a message to the nodeHandle
   */
  public void routeMyMsgDirect(NodeHandle nodeHandle) {
    Message msg = new PingMessage(endpoint.getId(), nodeHandle.getId());
    System.out.println(this + " sending direct ping message " + msg + " to " + nodeHandle);
    endpoint.route(null, msg, nodeHandle);
  }

  @Override
  public boolean forward(RouteMessage message) {
    return true;
  }

  @Override
  public void deliver(Id id, Message message) {
    System.out.println(this + ": received " + message);
  }

  @Override
  public void update(NodeHandle handle, boolean joined) {
    if (joined) {
      System.out.println(this + ": node joined - " + handle);
    } else {
      System.out.println(this + ": node left   - " + handle);
    }
  }

  @Override
  public String toString() {
    return "PingApp{" +
        "node=" + node.getId() +
        ", endpoint=" + endpoint.getId() +
        '}';
  }
}
