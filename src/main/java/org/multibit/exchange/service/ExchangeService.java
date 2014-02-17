package org.multibit.exchange.service;

import org.multibit.exchange.domain.model.*;

/**
 * <p>Service to provide the following to the applications in the infrastructure layer:</p>
 * <ul>
 * <li>Core exchange services.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface ExchangeService {

  void initializeExchange(ExchangeId identifier);

  void registerCurrencyPair(ExchangeId exchangeId, Ticker ticker, Currency baseCurrency, Currency counterCurrency);

  void placeOrder(ExchangeId exchangeId, SecurityOrder order);

  void registerCurrencyPair(ExchangeId exchangeId, CurrencyPair pair);
}
