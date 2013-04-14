package com.blurtty.peregrine.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>A MarketCollection maintains the complete set of {@link Market}'s.</p>
 *
 * @since 0.0.1
 *         
 */
public class MarketCollection {

  private Set<Market> markets;

  public MarketCollection() {
    markets = Sets.newHashSet();
  }

  /**
   * Adds a new market to the collection.
   *
   * @param market The market to add
   */
  public void add(Market market) {
    checkArgument(!markets.contains(market), "market already exists: %s", market.getSymbol());
    markets.add(market);
  }
}
