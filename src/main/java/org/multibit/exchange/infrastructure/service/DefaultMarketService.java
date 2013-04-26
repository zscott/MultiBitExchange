package org.multibit.exchange.infrastructure.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import org.multibit.exchange.domain.event.MarketAddedEvent;
import org.multibit.exchange.domain.event.MarketEventTopic;
import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.domain.market.MarketCollection;
import org.multibit.exchange.service.MarketService;
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

  private final MarketEventTopic marketEventTopic;

  @Inject
  public DefaultMarketService(MarketEventTopic marketEventTopic) {
    this.marketEventTopic = marketEventTopic;
    marketCollection = new MarketCollection();
  }

  @Override
  public void addMarket(String marketSymbol, String itemSymbol, String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(marketSymbol), "marketSymbol must not be null or empty: '%s'", marketSymbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "itemSymbol must not be null or empty: '%s'", itemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol must not be null or empty: '%s'", currencySymbol);

    Market market = new Market(marketSymbol, itemSymbol, currencySymbol);
    marketCollection.add(market);
    marketEventTopic.publish(new MarketAddedEvent(market));
  }
}
