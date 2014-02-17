package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventBasedExchangeServiceTest {

  private CommandGateway commandGateway = mock(CommandGateway.class);
  private DisruptorCommandBus commandBus = mock(DisruptorCommandBus.class);
  private EventBus eventBus = mock(EventBus.class);
  EventBasedExchangeService service;
  private ExchangeId exchangeId;

  @Before
  public void setUp() {
    exchangeId = ExchangeIdFaker.createValid();
    reset(commandGateway);
    reset(commandBus);
    reset(eventBus);
    service = new EventBasedExchangeService(commandGateway, commandBus, eventBus);
  }

  @Test
  public void testInitializeExchange() {
    // Arrange

    // Act
    service.initializeExchange(exchangeId);

    // Assert
    ArgumentCaptor<CreateExchangeCommand> argument = ArgumentCaptor.forClass(CreateExchangeCommand.class);
    verify(commandGateway, times(1)).send(argument.capture());
    assertThat(argument.getValue().getExchangeId()).isEqualTo(exchangeId);
  }

  @Test
  public void testRegisterCurrencyPair() throws Exception {
    // Arrange
    Currency expectedBaseCurrency = CurrencyFaker.createValid();
    Currency expectedCounterCurrency = CurrencyFaker.createValid();
    CurrencyPair currencyPair = new CurrencyPair(expectedBaseCurrency, expectedCounterCurrency);

    // Act
    service.registerCurrencyPair(exchangeId, currencyPair);

    // Assert
    ArgumentCaptor<RegisterCurrencyPairCommand> argument = ArgumentCaptor.forClass(RegisterCurrencyPairCommand.class);
    verify(commandGateway, times(1)).send(argument.capture());
    assertThat(argument.getValue().getExchangeId()).isEqualTo(exchangeId);
    assertThat(argument.getValue().getCurrencyPair().getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(argument.getValue().getCurrencyPair().getCounterCurrency()).isEqualTo(expectedCounterCurrency);  }
}
