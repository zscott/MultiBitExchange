package org.multibit.exchange.testing;

import org.multibit.exchange.cucumber.TradeRow;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.OrderBook;
import org.multibit.exchange.domain.model.Side;

import java.util.List;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public interface MatchingEngineTestFixture {
  ExchangeId getExchangeId();

  void given(Object... events);

  void when(Object... commands);

  List<TradeRow> getObservedTrades();

  void resetObservations();

  OrderBook getOrderBook(Side side);
}
