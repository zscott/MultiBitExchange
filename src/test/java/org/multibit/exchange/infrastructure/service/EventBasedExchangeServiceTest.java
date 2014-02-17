package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.*;
import org.multibit.exchange.testing.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EventBasedExchangeServiceTest {

  private CommandGateway commandGateway = mock(CommandGateway.class);
  private DisruptorCommandBus commandBus = mock(DisruptorCommandBus.class);
  private EventBus eventBus = mock(EventBus.class);
  EventBasedExchangeService service = new EventBasedExchangeService(commandGateway, commandBus, eventBus);

  @Test
  public void testInitializeExchange() {
    // Arrange
    ExchangeId expectedExchangeId = ExchangeIdFaker.createValid();

    // Act
    service.initializeExchange(expectedExchangeId);

    // Assert
    ArgumentCaptor<CreateExchangeCommand> argument = ArgumentCaptor.forClass(CreateExchangeCommand.class);
    verify(commandGateway, times(1)).send(argument.capture());
    assertThat(argument.getValue().getExchangeId()).isEqualTo(expectedExchangeId);
  }

  @Test
  public void testCreateSecurity() throws Exception {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    Ticker ticker = TickerFaker.createValid();
    Currency baseCurrency = CurrencyFaker.createValid();
    Currency counterCurrency = CurrencyFaker.createValid();

    // Act
    service.createSecurity(exchangeId, ticker, baseCurrency, counterCurrency);

    // Assert
    ArgumentCaptor<RegisterCurrencyPairCommand> argument = ArgumentCaptor.forClass(RegisterCurrencyPairCommand.class);
    verify(commandGateway, times(1)).send(argument.capture());
    assertThat(argument.getValue().getExchangeId()).isEqualTo(exchangeId);
    assertThat(argument.getValue().getCurrencyPair().getBaseCurrency()).isEqualTo(baseCurrency);
    assertThat(argument.getValue().getCurrencyPair().getCounterCurrency()).isEqualTo(counterCurrency);
  }
}
