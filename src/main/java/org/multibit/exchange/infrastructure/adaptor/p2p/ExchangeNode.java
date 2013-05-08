package org.multibit.exchange.infrastructure.adaptor.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import rice.environment.Environment;
import rice.pastry.Id;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * <p>Main class for starting an ExchangeNode</p>
 *
 * @since 0.0.1
 *         
 */
public class ExchangeNode {


  public ExchangeNode(int bindport, InetSocketAddress bootSocketAddress, Environment env) throws InterruptedException, IOException {

    // Generate the NodeIds Randomly
    NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

    // construct the PastryNodeFactory, this is how we use rice.pastry.socket
    PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

    // construct a node, but this does not cause it to boot
    PastryNode node = factory.newNode();

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

      PingApp app = new PingApp(node);
      System.out.println("PingApp initialized...");

      // wait 10 seconds
      System.out.println("Waiting 10 seconds...");
      env.getTimeSource().sleep(10000);

      // route 10 messages
      for (int i = 0; i < 10; i++) {
        // pick a key at random
        Id randId = nidFactory.generateNodeId();

        // send to that key
        app.routeMyMsg(randId);

        // wait a sec
        env.getTimeSource().sleep(1000);
      }

      // wait 10 seconds
      env.getTimeSource().sleep(10000);

      // send directly to my leafset
      LeafSet leafSet = node.getLeafSet();

      // this is a typical loop to cover your leafset.  Note that if the leafset
      // overlaps, then duplicate nodes will be sent to twice
      for (int i = -leafSet.ccwSize(); i <= leafSet.cwSize(); i++) {
        if (i != 0) { // don't send to self
          // select the item
          NodeHandle nh = leafSet.get(i);

          // send the message directly to the node
          app.routeMyMsgDirect(nh);

          // wait a sec
          env.getTimeSource().sleep(1000);
        }
      }
    }
  }

  public static void main(String[] args) {

    Environment env = new Environment();
    // disable the UPnP setting (in case you are testing this on a NATed LAN)
    env.getParameters().setString("nat_search_policy", "never");

    try {
      int bindport = Integer.parseInt(args[0]);
      InetAddress bootInetAddress = InetAddress.getByName(args[1]);
      int bootport = Integer.parseInt(args[2]);
      InetSocketAddress bootSocketAddress = new InetSocketAddress(bootInetAddress, bootport);

      new ExchangeNode(bindport, bootSocketAddress, env);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
