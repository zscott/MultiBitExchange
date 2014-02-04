package org.multibit.exchange.infrastructure.adaptor.events;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.testing.ExchangeIdFaker;

import static org.fest.assertions.api.Assertions.assertThat;

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
  public void equals() {
    // Arrange
    ExchangeId id = ExchangeIdFaker.createValid();
    CreateExchangeCommand command = new CreateExchangeCommand(id);

    // Act
    CreateExchangeCommand sameCommand = new CreateExchangeCommand(id);

    // Assert
    assertThat(command).isEqualTo(sameCommand);
  }


}
