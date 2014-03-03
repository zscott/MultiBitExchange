package org.multibit.exchange.testing;

import org.multibit.exchange.cucumber.TradeRow;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;

import java.util.List;

/**
 * <p>Fixture interface to provide the following to tests:</p>
 * <ul>
 * <li>A contract for implementing a Fixture for testing the ExchangeService.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public interface MatchingEngineTestFixture {

  ExchangeId getExchangeId();

  void resetObservations();

  void registerCurrencyPair(CurrencyPair pair);

  void placeOrder(SecurityOrder order);

  OrderBookReadModel getOrderBookReadModel(Side side);

  List<TradeRow> getObservedTrades();

  QuoteReadModel getQuoteReadModel();
}
