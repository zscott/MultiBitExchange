package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.events.ExchangeAggregateRoot;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;

import java.util.UUID;

/**
 * <p>TestBase to provide the following to {@link Security} related tests:</p>
 * <ul>
 * <li>Useful set of valid domain objects.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class ExchangeAggregateRootTestBase {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  protected ExchangeId exchangeId;
  protected Ticker ticker;
  protected Currency baseCurrency;
  protected Currency counterCurrency;
  protected FixtureConfiguration<ExchangeAggregateRoot> fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(ExchangeAggregateRoot.class);
    exchangeId = new ExchangeId("test-exchange:" + UUID.randomUUID().toString());
    ticker = TickerFaker.createValid();
    baseCurrency = CurrencyFaker.createValid();
    counterCurrency = CurrencyFaker.createValid();
  }
}
