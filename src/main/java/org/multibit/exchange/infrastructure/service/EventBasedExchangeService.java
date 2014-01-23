package org.multibit.exchange.infrastructure.service;

import javax.inject.Inject;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.infrastructure.adaptor.events.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.events.PlaceOrderCommand;
import org.multibit.exchange.infrastructure.adaptor.events.RegisterCurrencyPairCommand;
import org.multibit.exchange.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Service to provide the following to the application:</p>
 * <ul>
 * <li>Concrete implementation of {@link org.multibit.exchange.service.ExchangeService} based on the Axon Framework</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventBasedExchangeService implements ExchangeService {

  private static Logger LOGGER = LoggerFactory.getLogger(ExchangeService.class);

  private final CommandGateway commandGateway;
  private final DisruptorCommandBus commandBus;
  private final EventBus eventBus;


  @Inject
  public EventBasedExchangeService(
      CommandGateway commandGateway,
      DisruptorCommandBus commandBus,
      EventBus eventBus) {

    this.commandGateway = commandGateway;
    this.commandBus = commandBus;
    this.eventBus = eventBus;
  }

  @Override
  public void initializeExchange(ExchangeId identifier) {
    commandGateway.send(new CreateExchangeCommand(identifier));
  }

  @Override
  public void createSecurity(ExchangeId exchangeId, Ticker ticker, Currency tradeableItem, Currency currency) {
    commandGateway.send(new RegisterCurrencyPairCommand(exchangeId, ticker, tradeableItem, currency));
  }

  @Override
  public void placeOrder(ExchangeId exchangeId, SecurityOrder order) {
    commandGateway.send(new PlaceOrderCommand(exchangeId, order));
  }

  @Override
  public String toString() {
    return "DefaultApiService{" +
        "commandGateway=" + commandGateway +
        ", commandBus=" + commandBus +
        ", eventBus=" + eventBus +
        '}';
  }

}
