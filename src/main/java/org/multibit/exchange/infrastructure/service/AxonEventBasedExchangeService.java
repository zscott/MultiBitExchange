package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.PlaceOrderCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.SecurityOrder;
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
public class AxonEventBasedExchangeService implements ExchangeService {

  private final CommandGateway commandGateway;

  @Inject
  public AxonEventBasedExchangeService(
      CommandGateway commandGateway) {

    this.commandGateway = commandGateway;
  }

  @Override
  public void initializeExchange(ExchangeId identifier) {
    commandGateway.send(new CreateExchangeCommand(identifier));
  }

  @Override
  public void registerCurrencyPair(ExchangeId exchangeId, CurrencyPair currencyPair) {
    commandGateway.send(new RegisterCurrencyPairCommand(exchangeId, currencyPair));
  }

  @Override
  public void placeOrder(ExchangeId exchangeId, SecurityOrder order) {
    commandGateway.send(new PlaceOrderCommand(exchangeId, order));
  }

  @Override
  public String toString() {
    return "AxonEventBasedExchangeService{" +
        "commandGateway=" + commandGateway +
        '}';
  }
}
