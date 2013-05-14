package org.multibit.exchange.infrastructure.adaptor.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.multibit.exchange.domainmodel.RandomMarketOrderProvider;
import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Trade;
import rice.environment.Environment;
import rice.pastry.Id;
import rice.pastry.PastryNode;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * <p>Main class for starting an ExchangeNode</p>
 *
 * @since 0.0.1
 *         
 */
public class OrderNode {


  private final RandomNodeIdFactory nodeIdFactory;
  private final MultiBitExchangeApp multiBitExchangeApp;
  private final PastryNode node;
  private final Environment env;

  public OrderNode(
      int bindport,
      InetSocketAddress bootSocketAddress,
      Environment env) throws InterruptedException, IOException {
    this.env = env;

    nodeIdFactory = new RandomNodeIdFactory(env);
    node = new PastryNodeBooter(bindport, bootSocketAddress, env).boot();

    TradeHandler tradeHandler = new TradeHandler() {
      @Override
      public void handleTrade(Trade trade) {
        //System.out.println("observing trade");
      }
    };
    OrderHandler orderHandler = new OrderHandler() {
      @Override
      public void handleOrder(SecurityOrder order) {
        //System.out.println("ignoring order");
      }
    };
    multiBitExchangeApp = new MultiBitExchangeApp(node, tradeHandler, orderHandler);

    sendFakeOrders();
  }

  private void sendFakeOrders() throws InterruptedException {
    // wait 10 seconds
    System.out.println("Waiting 3 seconds before sending fake orders...");
    env.getTimeSource().sleep(3000);

    RandomMarketOrderProvider orderProvider = new RandomMarketOrderProvider();

    while (true) {
      // pick a key at random
      Id randId = nodeIdFactory.generateNodeId();

      SecurityOrder order = orderProvider.get();
      multiBitExchangeApp.routeOrder(randId, order);

      // wait 1/10 sec
      env.getTimeSource().sleep(10);
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

      new OrderNode(bindport, bootSocketAddress, env);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
