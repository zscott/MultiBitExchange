package org.multibit.exchange.infrastructure.adaptor.p2p;

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
public class PingMessage implements Message {

  public static final long serialVersionUID = 1L;

  protected Id from;

  protected Id to;

  protected String message;

  protected static int seq = 0;

  public PingMessage(Id from, Id to) {
    this.from = from;
    this.to = to;
    this.message = "ping #" + seq;
    seq++;
  }

  @Override
  public int getPriority() {
    return HIGH_PRIORITY;
  }

  @Override
  public String toString() {
    return "PingMessage{" +
        "message='" + message + '\'' +
        '}';
  }
}
