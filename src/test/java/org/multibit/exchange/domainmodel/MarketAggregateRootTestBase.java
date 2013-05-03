package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.events.MarketAggregateRoot;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

/**
 * <p>TestBase to provide the following to {@link Security} related tests:</p>
 * <ul>
 * <li>Useful set of valid domain objects.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class MarketAggregateRootTestBase {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  protected MarketId marketId;
  protected Ticker ticker;
  protected TradeableItem tradeableItem;
  protected Currency currency;
  protected FixtureConfiguration<MarketAggregateRoot> fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(MarketAggregateRoot.class);
    marketId = MarketId.get();
    ticker = TickerFaker.createValid();
    currency = CurrencyFaker.createValid();
    tradeableItem = TradeableItemFaker.createValid();
  }
}
