package org.multibit.exchange.infrastructure.adaptor.axon;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.domainmodel.CreateSecurityCommand;
import org.multibit.exchange.domainmodel.EventSourcedSecurity;
import org.multibit.exchange.domainmodel.SecurityCreatedEvent;
import org.multibit.exchange.domainmodel.SecurityId;

public class CreateSecurityCommandTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(EventSourcedSecurity.class);
  }

  @Test
  public void testCreateSecurity() {
    SecurityId id = SecurityId.next();
    String tickerSymbol = "LTC/BTC";
    String tradeableItemSymbol = "LTC";
    String currencySymbol = "BTC";

    fixture
        .given()
        .when(new CreateSecurityCommand(id, tickerSymbol, tradeableItemSymbol, currencySymbol))
        .expectEvents(new SecurityCreatedEvent(id, tickerSymbol, tradeableItemSymbol, currencySymbol));
  }
}
