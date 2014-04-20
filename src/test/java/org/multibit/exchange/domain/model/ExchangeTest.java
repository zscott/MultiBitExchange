package org.multibit.exchange.domain.model;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.event.CurrencyPairRemovedEvent;
import org.multibit.exchange.domain.event.ExchangeCreatedEvent;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RemoveTickerCommand;
import org.multibit.exchange.testing.CurrencyPairDescriptorFaker;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;

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
  public void addSecurity() throws DuplicateCurrencyPairSymbolException {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPairDescriptor cpd = CurrencyPairDescriptorFaker.createValid();
    RegisterCurrencyPairCommand registerCurrencyPairCommand
        = new RegisterCurrencyPairCommand(exchangeId, new CurrencyPairId(cpd.getSymbol()), cpd.getBaseCurrency(), cpd.getCounterCurrency());
    CurrencyPairRegisteredEvent expectedEvent
        = new CurrencyPairRegisteredEvent(exchangeId, new CurrencyPairId(cpd.getSymbol()), cpd.getBaseCurrency(), cpd.getCounterCurrency());

    // Given, When, Then
    fixture.given(new ExchangeCreatedEvent(exchangeId))
        .when(registerCurrencyPairCommand)
        .expectVoidReturnType()
        .expectEvents(expectedEvent);
  }

  @Test
  public void addDuplicateSecurity() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPairDescriptor cpd = CurrencyPairDescriptorFaker.createValid();
    CurrencyPairRegisteredEvent currencyPairRegisteredEvent
        = new CurrencyPairRegisteredEvent(exchangeId, new CurrencyPairId(cpd.getSymbol()), cpd.getBaseCurrency(), cpd.getCounterCurrency());

    // Given, When, Then
    RegisterCurrencyPairCommand registerCurrencyPairCommand =
        new RegisterCurrencyPairCommand(exchangeId, new CurrencyPairId(cpd.getSymbol()), cpd.getBaseCurrency(), cpd.getCounterCurrency());
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId),
            currencyPairRegisteredEvent)
        .when(
            registerCurrencyPairCommand)
        .expectException(DuplicateCurrencyPairSymbolException.class);
  }


  @Test
  public void removeSecurity() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPairDescriptor cpd = CurrencyPairDescriptorFaker.createValid();
    Currency baseCurrency = new Currency(cpd.getBaseCurrency());
    Currency counterCurrency = new Currency(cpd.getCounterCurrency());
    CurrencyPairRegisteredEvent currencyPairRegisteredEvent
        = new CurrencyPairRegisteredEvent(exchangeId, new CurrencyPairId(cpd.getSymbol()), cpd.getBaseCurrency(), cpd.getCounterCurrency());

    CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);

    // Given, When, Then
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId),
            currencyPairRegisteredEvent)
        .when(
            new RemoveTickerCommand(exchangeId, currencyPair.getTicker().getSymbol()))
        .expectVoidReturnType()
        .expectEvents(
            new CurrencyPairRemovedEvent(exchangeId, currencyPair.getTicker().getSymbol()));
  }

  @Test
  public void removeSecurity_DoesntExist() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Given, When, Then
    fixture
        .given(
            new ExchangeCreatedEvent(exchangeId))
        .when(
            new RemoveTickerCommand(exchangeId, currencyPair.getTicker().getSymbol()))
        .expectException(NoSuchTickerException.class);
  }
}
