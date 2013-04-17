package com.blurtty.peregrine.service;

/**
 * <p>Service to provide the following to all Resources:</p>
 * <ul>
 * <li>Peregrine Application Functionality (as opposed to simple display of current state)</li>
 * </ul>
 *
 * @since: 0.0.1
 */
public interface MarketService {
  void addMarket(String symbol, String itemSymbol, String currencySymbol);
}
