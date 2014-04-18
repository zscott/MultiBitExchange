package org.multibit.exchange.testing;

import org.multibit.exchange.cucumber.TradeRow;
import org.multibit.exchange.domain.command.CurrencyPairDescriptor;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.command.OrderDescriptor;
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

  void registerCurrencyPair(CurrencyPairDescriptor currencyPair);

  void placeOrder(OrderDescriptor order);

  OrderBookReadModel getOrderBookReadModel(Side side);

  List<TradeRow> getObservedTrades();

  QuoteReadModel getQuoteReadModel();
}
