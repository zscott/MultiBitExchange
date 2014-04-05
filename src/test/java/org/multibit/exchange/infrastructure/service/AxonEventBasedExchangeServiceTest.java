package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AxonEventBasedExchangeServiceTest {

  private CommandGateway commandGateway = mock(CommandGateway.class);
  AxonEventBasedExchangeService service;
  private ExchangeId exchangeId;

  ArgumentCaptor<Long> timeout = ArgumentCaptor.forClass(Long.class);
  ArgumentCaptor<TimeUnit> unit = ArgumentCaptor.forClass(TimeUnit.class);

  @Before
  public void setUp() {
    exchangeId = ExchangeIdFaker.createValid();
    reset(commandGateway);
    service = new AxonEventBasedExchangeService(commandGateway);
  }

  @Test
  public void testInitializeExchange() {
    // Arrange

    // Act
    service.initializeExchange(exchangeId);

    // Assert
    ArgumentCaptor<CreateExchangeCommand> argument = ArgumentCaptor.forClass(CreateExchangeCommand.class);
    verify(commandGateway, times(1)).sendAndWait(argument.capture(), timeout.capture(), unit.capture());
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
    verify(commandGateway, times(1)).sendAndWait(argument.capture(), timeout.capture(), unit.capture());
    assertThat(argument.getValue().getExchangeId()).isEqualTo(exchangeId);
    assertThat(argument.getValue().getCurrencyPair().getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(argument.getValue().getCurrencyPair().getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }
}
