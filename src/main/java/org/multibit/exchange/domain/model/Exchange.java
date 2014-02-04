package org.multibit.exchange.domain.model;

import com.google.common.collect.Maps;
import org.multibit.exchange.domain.event.DomainEvents;
import org.multibit.exchange.domain.event.OrderAccepted;

import javax.inject.Inject;
import java.util.Map;

/**
 * <p>An exchange containing many securities.</p>
 *
 * @since 0.0.1
 */
public class Exchange {

  private Map<Ticker, MatchingEngine> matchingEngineMap = Maps.newHashMapWithExpectedSize(17);
  private ExchangeId id;

  @Inject
  public Exchange() {
    this.setId(id);
  }

  // todo: remove ticker parameter
  public void addSecurity(Ticker ticker, CurrencyPair currencyPair) throws DuplicateTickerException {
    if (matchingEngineMap.containsKey(ticker))
      throw new DuplicateTickerException(ticker);

    OrderBook buyBook = new OrderBook(Side.BUY);
    OrderBook sellBook = new OrderBook(Side.SELL);
    matchingEngineMap.put(ticker, new MatchingEngine(ticker, buyBook, sellBook));
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

    DomainEvents.raise(new OrderAccepted(order));
    matchingEngineMap.get(ticker).acceptOrder(order);
  }

  public ExchangeId getId() {
    return id;
  }

  public void setId(ExchangeId id) {
    this.id = id;
  }
}
