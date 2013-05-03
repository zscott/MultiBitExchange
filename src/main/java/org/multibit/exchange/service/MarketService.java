package org.multibit.exchange.service;

import org.multibit.exchange.domainmodel.Currency;
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
public interface MarketService {
  void createSecurity(Ticker ticker, TradeableItem tradeableItem, Currency currency);
}
