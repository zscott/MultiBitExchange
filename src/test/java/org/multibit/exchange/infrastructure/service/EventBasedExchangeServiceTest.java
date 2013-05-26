package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.ExchangeTestFixture;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.infrastructure.adaptor.events.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.events.RegisterCurrencyPairCommand;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.TickerFaker;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventBasedExchangeServiceTest {

  private ExchangeTestFixture fixture = new ExchangeTestFixture();
  private CommandGateway commandGateway = mock(CommandGateway.class);
  private DisruptorCommandBus commandBus = mock(DisruptorCommandBus.class);
  private EventBus eventBus = mock(EventBus.class);
  EventBasedExchangeService service = new EventBasedExchangeService(commandGateway, commandBus, eventBus);

  @Test
  public void testInitializeExchange() {
    // Arrange
    CreateExchangeCommand expectedCommand = new CreateExchangeCommand(fixture.getExchangeId());

    // Act
    service.initializeExchange(fixture.getExchangeId());

    // Assert
    verify(commandGateway, times(1)).send(expectedCommand);
  }


  @Test
  public void testCreateSecurity() throws Exception {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    Ticker ticker = TickerFaker.createValid();
    Currency baseCurrency = CurrencyFaker.createValid();
    Currency counterCurrency = CurrencyFaker.createValid();

    RegisterCurrencyPairCommand expectedCommand =
        new RegisterCurrencyPairCommand(
            exchangeId,
            ticker,
            baseCurrency,
            counterCurrency);

    // Act
    service.createSecurity(exchangeId, ticker, baseCurrency, counterCurrency);

    // Assert
    verify(commandGateway, times(1)).send(expectedCommand);
  }
}
