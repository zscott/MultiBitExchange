package com.blurtty.peregrine.service;

/**
 * <p>Service to provide the following to all Resources:</p>
 * <ul>
 * <li>Peregrine Application Functionality (as opposed to simple display of current state)</li>
 * </ul>
 *
 * @since: 0.0.1
 */
public interface ApplicationService {
  void createMarket(String marketSymbol, String itemCurrency, String pricedInCurrency);
}
