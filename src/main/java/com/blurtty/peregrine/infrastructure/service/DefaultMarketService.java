package com.blurtty.peregrine.infrastructure.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.domain.market.Market;
import com.blurtty.peregrine.domain.market.MarketAddedEvent;
import com.blurtty.peregrine.domain.market.MarketCollection;
import com.blurtty.peregrine.domain.market.MarketEventPublisher;


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

  private final MarketEventPublisher marketEventPublisher;

  @Inject
  public DefaultMarketService(MarketEventPublisher marketEventPublisher) {
    this.marketEventPublisher = marketEventPublisher;
    marketCollection = new MarketCollection();
  }

  @Override
  public void addMarket(String symbol, String itemSymbol, String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "market symbol must not be null or empty: '%s'", symbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "item symbol must not be null or empty: '%s'", itemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currency symbol must not be null or empty: '%s'", currencySymbol);

    Market market = new Market(symbol, itemSymbol, currencySymbol);
    marketCollection.add(market);
    marketEventPublisher.publish(new MarketAddedEvent(market));
  }
}
