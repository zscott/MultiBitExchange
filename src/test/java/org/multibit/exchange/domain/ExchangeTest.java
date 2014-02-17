package org.multibit.exchange.domain;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.command.RemoveCurrencyPairCommand;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.event.CurrencyPairRemovedEvent;
import org.multibit.exchange.domain.event.ExchangeCreatedEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.DuplicateTickerException;
import org.multibit.exchange.domain.model.Exchange;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.NoSuchTickerException;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.TickerFaker;

public class ExchangeTest {

  private FixtureConfiguration<Exchange> fixture;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Exchange.class);
  }

  @Test
  public void createExchange() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();

    // Given, When, Then
    fixture
        .given()
        .when(
            new CreateExchangeCommand(exchangeId))
        .expectEvents(
            new ExchangeCreatedEvent(exchangeId));
  }

  @Test
  public void addSecurity() throws DuplicateTickerException {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Given, When, Then
    fixture.given(new ExchangeCreatedEvent(exchangeId))
        .when(new RegisterCurrencyPairCommand(exchangeId, currencyPair))
        .expectVoidReturnType()
        .expectEvents(new CurrencyPairRegisteredEvent(exchangeId, currencyPair));
  }

  @Test
  public void addDuplicateSecurity() throws DuplicateTickerException {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Given, When, Then
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId),
            new CurrencyPairRegisteredEvent(exchangeId, currencyPair))
        .when(
            new RegisterCurrencyPairCommand(exchangeId, currencyPair))
        .expectException(DuplicateTickerException.class);
  }


  @Test
  public void removeSecurity() throws DuplicateTickerException, NoSuchTickerException {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Given, When, Then
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId),
            new CurrencyPairRegisteredEvent(exchangeId, currencyPair))
        .when(
            new RemoveCurrencyPairCommand(exchangeId, currencyPair))
        .expectVoidReturnType()
        .expectEvents(
            new CurrencyPairRemovedEvent(exchangeId, currencyPair));
  }

  @Test
  public void removeSecurity_DoesntExist() throws DuplicateTickerException, NoSuchTickerException {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    Ticker ticker = TickerFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Given, When, Then
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId))
        .when(
            new RemoveCurrencyPairCommand(exchangeId, currencyPair))
        .expectException(NoSuchTickerException.class);
  }

}
