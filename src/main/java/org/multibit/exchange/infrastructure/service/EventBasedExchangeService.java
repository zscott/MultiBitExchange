package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.PlaceOrderCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.service.ExchangeService;

import javax.inject.Inject;

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

  private final CommandGateway commandGateway;
  private final CommandBus commandBus;
  private final EventBus eventBus;


  @Inject
  public EventBasedExchangeService(
      CommandGateway commandGateway,
      CommandBus commandBus,
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
  public void registerCurrencyPair(ExchangeId exchangeId, Ticker ticker, Currency baseCurrency, Currency counterCurrency) {
    //fixme - remove this method eventually
    registerCurrencyPair(exchangeId, new CurrencyPair(baseCurrency, counterCurrency));
  }

  @Override
  public void placeOrder(ExchangeId exchangeId, SecurityOrder order) {
    commandGateway.send(new PlaceOrderCommand(exchangeId, order));
  }

  @Override
  public void registerCurrencyPair(ExchangeId exchangeId, CurrencyPair pair) {
    commandGateway.send(new RegisterCurrencyPairCommand(exchangeId, pair));
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
