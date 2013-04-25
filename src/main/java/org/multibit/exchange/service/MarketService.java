package org.multibit.exchange.service;

/**
 * <p>Service to provide the following to the infrastructure and service layers:</p>
 * <ul>
 * <li>MultiBit Exchange Application Functionality (as opposed to simple display of current state)</li>
 * </ul>
 *
 * @since: 0.0.1
 */
public interface MarketService {
  void addMarket(String symbol, String itemSymbol, String currencySymbol);
}
