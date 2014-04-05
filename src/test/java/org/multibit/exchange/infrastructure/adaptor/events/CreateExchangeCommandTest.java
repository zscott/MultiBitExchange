package org.multibit.exchange.infrastructure.adaptor.events;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.testing.ExchangeIdFaker;

public class CreateExchangeCommandTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void create_NullId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

    // Act
    new CreateExchangeCommand(null);
  }

  @Test
  public void create_FakerValidId() {
    // Arrange

    // Act
    new CreateExchangeCommand(ExchangeIdFaker.createValid());
  }

  @Test
  public void create_ValidId() {
    // Arrange
    ExchangeId validId = new ExchangeId("valid-id");

    // Act
    new CreateExchangeCommand(validId);
  }

  @Test
  public void create_DefaultId() {
    // Arrange
    ExchangeId defaultId = new ExchangeId();

    // Act
    new CreateExchangeCommand(defaultId);
  }

}
