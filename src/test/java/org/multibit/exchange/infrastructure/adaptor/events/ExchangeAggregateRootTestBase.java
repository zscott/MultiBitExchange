package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domainmodel.CurrencyPair;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.testing.CurrencyPairFaker;

import java.util.UUID;

/**
 * <p>TestBase to provide the following to {@link ExchangeAggregateRoot} related tests:</p>
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
  protected CurrencyPair currencyPair;
  protected FixtureConfiguration<ExchangeAggregateRoot> fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(ExchangeAggregateRoot.class);
    exchangeId = new ExchangeId("test-exchange:" + UUID.randomUUID().toString());
    currencyPair = CurrencyPairFaker.createValid();
  }
}
