package org.multibit.exchange.service;

import org.multibit.exchange.domain.command.CurrencyPairDescriptor;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.command.OrderDescriptor;
import org.multibit.exchange.domain.command.OrderId;

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

  void registerTicker(ExchangeId exchangeId, CurrencyPairDescriptor currencyPair);

  void placeOrder(ExchangeId exchangeId, OrderId orderId, OrderDescriptor order);
}
