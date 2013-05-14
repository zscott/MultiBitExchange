package org.multibit.exchange.infrastructure.adaptor.p2p;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import rice.environment.Environment;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

public class FreePastrySmokeTest {

  private Environment env = new Environment();
  private int bindport = 5309; // Jenny's number: 867-5309
  private String bootAddressString = "127.0.0.1";
  private int bootPort = 5308;

  @Before
  public void setUp() throws IOException, InterruptedException {

    // disable the UPnP setting (in case you are testing this on a NATed LAN)
    env.getParameters().setString("nat_search_policy", "never");
    InetSocketAddress bootAddress = new InetSocketAddress(bootAddressString, bootPort);

    // Generate the NodeIds Randomly
    NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

    // construct the PastryNodeBooter, this is how we use rice.pastry.socket
    PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

    // construct a node, but this does not cause it to boot
    PastryNode node = factory.newNode();

    // in later tutorials, we will register applications before calling boot
    node.boot(bootAddress);

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

    System.out.println("Finished creating new node " + node);
  }

  @Test
  @Ignore
  public void test() {
    // Arrange

    // Act

    // Assert

  }

}
