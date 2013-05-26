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

  private Map<Ticker, OrderBook> orderBookMap = Maps.newHashMapWithExpectedSize(17);

  // todo: remove ticker parameter
  public void addSecurity(Ticker ticker, CurrencyPair currencyPair) throws DuplicateTickerException {
    if (orderBookMap.containsKey(ticker))
      throw new DuplicateTickerException(ticker);

    orderBookMap.put(ticker, new OrderBook(currencyPair));
  }

  public void removeSecurity(Ticker ticker) throws NoSuchTickerException {
    if (!orderBookMap.containsKey(ticker))
      throw new NoSuchTickerException(ticker);

    orderBookMap.remove(ticker);
  }

  public Optional<Trade> placeOrder(SecurityOrder order) throws NoSuchTickerException, DuplicateOrderException {
    Ticker ticker = order.getCurrencyPair().getTicker();
    if (!orderBookMap.containsKey(ticker))
      throw new NoSuchTickerException(ticker);

    return orderBookMap.get(ticker).addOrderAndExecuteTrade(order);
  }
}
