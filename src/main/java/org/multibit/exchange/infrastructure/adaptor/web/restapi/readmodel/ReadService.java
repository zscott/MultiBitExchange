package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

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
public interface ReadService {
  List<CurrencyPairReadModel> fetchSecurities(String exchangeId);

  List<OrderReadModel> fetchOpenOrders(String tickerSymbol);

  List<QuoteReadModel> fetchQuotes(String exchangeId);
}
