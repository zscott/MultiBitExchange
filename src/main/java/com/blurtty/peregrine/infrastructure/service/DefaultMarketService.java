package com.blurtty.peregrine.infrastructure.service;

import com.blurtty.peregrine.domain.Market;
import com.blurtty.peregrine.domain.MarketAddedEvent;
import com.blurtty.peregrine.domain.MarketCollection;
import com.blurtty.peregrine.domain.MarketEventPublisherService;
import com.blurtty.peregrine.service.MarketService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Concrete implementation to provide the following to MarketService:</p>
 * <ul>
 * <li>Translates calls from infrastructure layer into domain layer</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class DefaultMarketService implements MarketService {

  private static final Logger log = LoggerFactory.getLogger(DefaultMarketService.class);

  private final MarketCollection marketCollection;

  private final MarketEventPublisherService marketEventPublisherService;

  @Inject
  public DefaultMarketService(MarketEventPublisherService marketEventPublisherService) {
    this.marketEventPublisherService = marketEventPublisherService;
    marketCollection = new MarketCollection();
  }

  @Override
  public void addMarket(String symbol, String itemSymbol, String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "market symbol must not be null or empty: '%s'", symbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "item symbol must not be null or empty: '%s'", itemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currency symbol must not be null or empty: '%s'", currencySymbol);

    Market market = new Market(symbol, itemSymbol, currencySymbol);
    marketCollection.add(market);
    marketEventPublisherService.publish(new MarketAddedEvent(market));
  }
}
