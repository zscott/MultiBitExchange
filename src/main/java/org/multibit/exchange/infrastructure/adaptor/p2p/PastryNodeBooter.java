package org.multibit.exchange.infrastructure.adaptor.p2p;

import java.io.IOException;
import java.net.InetSocketAddress;
import rice.environment.Environment;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

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
public class PastryNodeBooter {
  private int bindport;
  private InetSocketAddress bootSocketAddress;
  private Environment env;
  private NodeIdFactory nidFactory;
  private PastryNode node;

  public PastryNodeBooter(
      int bindport,
      InetSocketAddress bootSocketAddress,
      Environment env) {
    this.nidFactory = new RandomNodeIdFactory(env);
    this.bindport = bindport;
    this.bootSocketAddress = bootSocketAddress;
    this.env = env;
  }

  public PastryNode boot() throws IOException, InterruptedException {
    // Generate the NodeIds Randomly

    // construct the PastryNodeBooter, this is how we use rice.pastry.socket
    rice.pastry.PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

    // construct a node, but this does not cause it to boot
    node = factory.newNode();

    // in later tutorials, we will register applications before calling boot
    System.out.println("Booting using address: " + bootSocketAddress);
    node.boot(bootSocketAddress);

    // the node may require sending several messages to fully boot into the ring
    synchronized (node) {
      while (!node.isReady() && !node.joinFailed()) {
        // delay so we don't busy-wait
        node.wait(500);

        // abort if can't join
        if (node.joinFailed()) {
          throw new IOException("Could not join the FreePastry ring.  Reason:" + node.joinFailedReason());
        }
      }
    }
    return node;
  }
}
