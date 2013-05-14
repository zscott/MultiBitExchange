package org.multibit.exchange.infrastructure.adaptor.p2p;

import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.DeliveryNotification;
import rice.p2p.commonapi.MessageReceipt;
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
public abstract class AbstractPastryApplication implements Application, DeliveryNotification {

  @Override
  public boolean forward(RouteMessage message) {
    return true;
  }

  @Override
  public void update(NodeHandle handle, boolean joined) {
    if (joined) {
      System.out.println("node joined - " + handle);
    } else {
      System.out.println("node left   - " + handle);
    }
  }

  public void sent(MessageReceipt msg) {
//    System.out.println("sent message SUCCESSFULLY");
  }

  public void sendFailed(MessageReceipt msg, Exception reason) {
    System.out.println("***FAILED*** to send message " + msg + "\nreason: " + reason);
  }
}
