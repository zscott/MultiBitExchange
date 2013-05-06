package org.multibit.exchange.service;

import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.ItemQuantity;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;

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

  void createSecurity(ExchangeId exchangeId, Ticker ticker, TradeableItem tradeableItem, Currency currency);

  void placeBidOrder(ExchangeId exchangeId, Ticker ticker, ItemQuantity itemQuantity);
}
