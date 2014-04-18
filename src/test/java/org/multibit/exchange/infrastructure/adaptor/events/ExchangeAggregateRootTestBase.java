package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.Exchange;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

/**
 * <p>TestBase to provide the following to {@link org.multibit.exchange.domain.model.Exchange} related tests:</p>
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
  protected FixtureConfiguration<Exchange> fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Exchange.class);
    exchangeId = new ExchangeId();
  }
}
