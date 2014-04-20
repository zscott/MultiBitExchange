package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
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
    String baseCurrency = "BTC";
    String counterCurrency = "LTC";
    String expectedCurrencyPairSymbol = "BTC/LTC";

    CurrencyPairDescriptor currencyPairDescriptor = new CurrencyPairDescriptor(baseCurrency, counterCurrency);

    // Act
    service.registerCurrencyPair(exchangeId, currencyPairDescriptor);

    // Assert
    ArgumentCaptor<RegisterCurrencyPairCommand> commandCaptor = ArgumentCaptor.forClass(RegisterCurrencyPairCommand.class);
    verify(commandGateway, times(1)).sendAndWait(commandCaptor.capture(), timeout.capture(), unit.capture());
    RegisterCurrencyPairCommand actualCommand = commandCaptor.getValue();
    assertThat(actualCommand.getExchangeId()).isEqualTo(exchangeId);
    assertThat(actualCommand.getSymbol()).isEqualTo(expectedCurrencyPairSymbol);
  }
}
