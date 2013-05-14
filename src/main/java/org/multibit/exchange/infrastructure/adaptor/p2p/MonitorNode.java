package org.multibit.exchange.infrastructure.adaptor.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Trade;
import rice.environment.Environment;
import rice.environment.time.simple.SimpleTimeSource;
import rice.pastry.PastryNode;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * <p>Main class for starting an ExchangeNode</p>
 *
 * @since 0.0.1
 *         
 */
public class MonitorNode {


  private final RandomNodeIdFactory nodeIdFactory;
  private final MultiBitExchangeApp multiBitExchangeApp;
  private final PastryNode node;
  private final Environment env;
  private final SimpleTimeSource simpleTimeSource = new SimpleTimeSource();
  private long trades = 0;
  private long orders = 0;
  private long timingCheckpoint;

  public MonitorNode(
      int bindport,
      InetSocketAddress bootSocketAddress,
      Environment env) throws InterruptedException, IOException {
    this.env = env;

    nodeIdFactory = new RandomNodeIdFactory(env);
    node = new PastryNodeBooter(bindport, bootSocketAddress, env).boot();

    TradeHandler tradeHandler = new TradeHandler() {
      @Override
      public void handleTrade(Trade trade) {
        countTrade();
      }
    };
    OrderHandler orderHandler = new OrderHandler() {
      @Override
      public void handleOrder(SecurityOrder order) {
        countOrder();
      }
    };
    multiBitExchangeApp = new MultiBitExchangeApp(node, tradeHandler, orderHandler);
    timingCheckpoint = simpleTimeSource.currentTimeMillis();
  }

  private void countOrder() {
    orders++;
    outputStats();
  }

  private void outputStats() {
    long newTimingCheckpoint = simpleTimeSource.currentTimeMillis();
    long elapsedTime = newTimingCheckpoint - timingCheckpoint;
    if (elapsedTime >= 2000) {
      long ordersPerSec = orders / (elapsedTime / 1000);
      long tradesPerSec = trades / (elapsedTime / 1000);
      System.out.println("");
      System.out.println("--------------------------------------------");
      System.out.println("observing " + ordersPerSec + " orders/second");
      System.out.println("observing " + tradesPerSec + " trades/second");
      System.out.println("--------------------------------------------");
      orders = 0;
      trades = 0;
      timingCheckpoint = newTimingCheckpoint;
    }
  }

  private void countTrade() {
    trades++;
    outputStats();
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

      new MonitorNode(bindport, bootSocketAddress, env);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
