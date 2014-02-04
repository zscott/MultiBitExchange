package org.multibit.exchange.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.*;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.TickerFaker;

public class ExchangeTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void Create() {
    // Arrange

    // Act
    new Exchange();

    // Assert

  }

  @Test
  public void addSecurity() throws DuplicateTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    // Act
    exchange.addSecurity(ticker, currencyPair);

    // Assert

  }

  @Test
  public void addDuplicateSecurity() throws DuplicateTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    exchange.addSecurity(ticker, currencyPair);
    thrown.expect(DuplicateTickerException.class);
    thrown.expectMessage("ticker " + ticker.getSymbol() + " already exists");

    // Act
    exchange.addSecurity(ticker, currencyPair);
  }


  @Test
  public void addRemoveSecurity() throws DuplicateTickerException, NoSuchTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    exchange.addSecurity(ticker, currencyPair);

    // Act
    exchange.removeSecurity(ticker);
  }

  @Test
  public void addRemoveSecurity_DoesntExist() throws DuplicateTickerException, NoSuchTickerException {
    // Arrange
    Exchange exchange = new Exchange();
    Ticker ticker = TickerFaker.createValid();

    thrown.expect(NoSuchTickerException.class);
    thrown.expectMessage("no such ticker " + ticker.getSymbol());

    // Act
    exchange.removeSecurity(ticker);
  }

}
