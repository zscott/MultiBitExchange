package org.multibit.exchange.service;

import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;

import java.util.List;

/**
 * <p>ReadService to provide the following to the application:</p>
 * <ul>
 * <li>Read-only access to securities information</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public interface QueryProcessor {
  List<CurrencyPairReadModel> fetchSecurities(String exchangeId);

  List<QuoteReadModel> fetchQuotes(String exchangeId);

  OrderBookReadModel fetchOrderBook(String exchangeId, String tickerSymbol);
}
