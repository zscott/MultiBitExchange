package org.multibit.exchange.infrastructure.adaptor.axon;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.domain.CreateSecurityCommand;
import org.multibit.exchange.domain.SecurityCreatedEvent;

public class CreateSecurityCommandTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(EventSourcedSecurity.class);
  }

  @Test
  public void testCreateSecurity() {
    String tickerSymbol = "LTC/BTC";
    String tradeableItemSymbol = "LTC";
    String currencySymbol = "BTC";

    fixture
        .given()
        .when(new CreateSecurityCommand(tickerSymbol, tradeableItemSymbol, currencySymbol))
        .expectEvents(new SecurityCreatedEvent(tickerSymbol, tradeableItemSymbol, currencySymbol));
  }
}
