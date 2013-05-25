package org.multibit.exchange.infrastructure.adaptor.p2p;

import com.google.common.base.Optional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.DuplicateOrderException;
import org.multibit.exchange.domainmodel.Security;
import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.Trade;
import org.multibit.exchange.domainmodel.CurrencyPair;
import rice.environment.Environment;
import rice.p2p.commonapi.Id;
import rice.pastry.PastryNode;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * <p>Main class for starting an ExchangeNode</p>
 *
 * @since 0.0.1
 *         
 */
public class ExchangeNode {


  private final RandomNodeIdFactory nodeIdFactory;
  private final PastryNode node;
  private final MultiBitExchangeApp multiBitExchangeApp;
  private final Security security;

  public ExchangeNode(
      int bindport,
      InetSocketAddress bootSocketAddress,
      Environment env) throws InterruptedException, IOException {

    nodeIdFactory = new RandomNodeIdFactory(env);
    node = new PastryNodeBooter(bindport, bootSocketAddress, env).boot();


    Ticker ticker = new Ticker("BTC/LTC");
    Currency base = new Currency("BTC");
    Currency counterCurrency = new Currency("LTC");
    CurrencyPair currencyPair = new CurrencyPair(base, counterCurrency);
    security = new Security(ticker, currencyPair);

    TradeHandler tradeHandler = new TradeHandler() {
      @Override
      public void handleTrade(Trade trade) {
        //System.out.println("ignoring trade");
      }
    };

    OrderHandler orderHandler = new OrderHandler() {
      @Override
      public void handleOrder(SecurityOrder order) {
//        System.out.println("placing order " + order);
        try {
          Optional<Trade> tradeOptional = security.placeOrder(order);
          if (tradeOptional.isPresent()) {
            Id randId = nodeIdFactory.generateNodeId();
            multiBitExchangeApp.routeTrade(randId, tradeOptional.get());
          }
        } catch (DuplicateOrderException e) {
          e.printStackTrace();
        }
      }
    };

    multiBitExchangeApp = new MultiBitExchangeApp(node, tradeHandler, orderHandler);
  }

  public static void main(String[] args) {
    String bindportArg = args[0];
    String bootAddressArg = args[1];
    String bootPortArg = args[2];
    startup(bindportArg, bootAddressArg, bootPortArg);
  }

  private static void startup(String bindportArg, String bootAddressArg, String bootPortArg) {
    Environment env = new Environment();
    // disable the UPnP setting (in case you are testing this on a NATed LAN)
    env.getParameters().setString("nat_search_policy", "never");

    try {
      int bindport = Integer.parseInt(bindportArg);
      InetAddress bootInetAddress = InetAddress.getByName(bootAddressArg);
      int bootport = Integer.parseInt(bootPortArg);
      InetSocketAddress bootSocketAddress = new InetSocketAddress(bootInetAddress, bootport);

      new ExchangeNode(bindport, bootSocketAddress, env);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
