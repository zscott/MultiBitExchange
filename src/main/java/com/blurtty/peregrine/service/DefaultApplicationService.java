package com.blurtty.peregrine.service;

import com.blurtty.peregrine.domain.Market;
import com.blurtty.peregrine.domain.MarketCollection;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Concrete implementation to provide the following to ApplicationService:</p>
 * <ul>
 * <li>Translates calls from infrastructure layer into domain layer</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class DefaultApplicationService implements ApplicationService {

  private static final Logger log = LoggerFactory.getLogger(DefaultApplicationService.class);

  private final MarketCollection marketCollection;

  @Inject
  public DefaultApplicationService() {
    marketCollection = new MarketCollection();
  }

  @Override
  public void addMarket(String symbol, String itemSymbol, String currencySymbol) {

    checkNotNull(symbol, "market symbol must not be null or empty: '%s'", symbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "item symbol must not be null or empty: '%s'", itemSymbol);
    checkNotNull(currencySymbol, "currency symbol must not be null or empty: '%s'", currencySymbol);

    Market market = new Market(symbol, itemSymbol, currencySymbol);
    marketCollection.add(market);
  }
}
