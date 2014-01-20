package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * <p>An exchange containing many securities.</p>
 *
 * @since 0.0.1
 */
public class Exchange {

  private Map<Ticker, MatchingEngine> matchingEngineMap = Maps.newHashMapWithExpectedSize(17);

  // todo: remove ticker parameter
  public void addSecurity(Ticker ticker, CurrencyPair currencyPair) throws DuplicateTickerException {
    if (matchingEngineMap.containsKey(ticker))
      throw new DuplicateTickerException(ticker);

    OrderBook buyBook = new OrderBook(Side.BUY);
    OrderBook sellBook = new OrderBook(Side.SELL);
    matchingEngineMap.put(ticker, new MatchingEngine(buyBook, sellBook));
  }

  public void removeSecurity(Ticker ticker) throws NoSuchTickerException {
    if (!matchingEngineMap.containsKey(ticker))
      throw new NoSuchTickerException(ticker);

    matchingEngineMap.remove(ticker);
  }

  public void placeOrder(SecurityOrder order) throws NoSuchTickerException, DuplicateOrderException {
    Ticker ticker = order.getTicker();
    if (!matchingEngineMap.containsKey(ticker))
      throw new NoSuchTickerException(ticker);

    matchingEngineMap.get(ticker).acceptOrder(order);
  }
}
