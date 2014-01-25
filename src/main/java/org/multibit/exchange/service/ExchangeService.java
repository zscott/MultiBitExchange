package org.multibit.exchange.service;

import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Ticker;

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

  void createSecurity(ExchangeId exchangeId, Ticker ticker, Currency tradeableItem, Currency currency);

  void placeOrder(ExchangeId exchangeId, SecurityOrder order);

}
