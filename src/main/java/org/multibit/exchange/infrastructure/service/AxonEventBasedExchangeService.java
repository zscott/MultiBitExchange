package org.multibit.exchange.infrastructure.service;

import com.google.common.base.Preconditions;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.AggregateNotFoundException;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.PlaceOrderCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
import org.multibit.exchange.service.ExchangeService;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

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

  private static final long TIMEOUT = 1;

  @Inject
  public AxonEventBasedExchangeService(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @Override
  public void initializeExchange(ExchangeId exchangeId) {
    Preconditions.checkNotNull(exchangeId, "exchangeId must not be null");
    CreateExchangeCommand command = new CreateExchangeCommand(exchangeId);
    safeSendAndWait(command);
  }

  @Override
  public void registerCurrencyPair(ExchangeId exchangeId, CurrencyPairId currencyPairId, CurrencyId baseCurrencyId, CurrencyId counterCurrencyId) {
    Preconditions.checkNotNull(exchangeId, "exchangeId must not be null");
    Preconditions.checkNotNull(currencyPairId, "currencyPairId must not be null");
    Preconditions.checkNotNull(baseCurrencyId, "baseCurrencyId must not be null");
    Preconditions.checkNotNull(counterCurrencyId, "counterCurrencyId must not be null");
    RegisterCurrencyPairCommand command
        = new RegisterCurrencyPairCommand(
        exchangeId,
        currencyPairId,
        baseCurrencyId,
        counterCurrencyId);
    safeSendAndWait(command);
  }

  @Override
  public void placeOrder(ExchangeId exchangeId, OrderId orderId, OrderDescriptor orderDescriptor) {
    Preconditions.checkNotNull(exchangeId, "exchangeId must not be null");
    Preconditions.checkNotNull(orderId, "orderId must not be null");
    Preconditions.checkNotNull(orderDescriptor, "orderDescriptor must not be null");
    PlaceOrderCommand command = new PlaceOrderCommand(exchangeId, orderDescriptor);
    safeSendAndWait(command);
  }

  private void safeSendAndWait(ExchangeCommand command) {
    try {
      commandGateway.sendAndWait(command, TIMEOUT, TimeUnit.SECONDS);
    } catch (AggregateNotFoundException e) {
      throw new NoSuchExchangeException(command.getExchangeId(), e);
    }
  }
}
