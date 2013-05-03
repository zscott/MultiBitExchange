package org.multibit.exchange.infrastructure.service;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.events.CreateSecurityCommand;
import org.multibit.exchange.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * <p>Service to provide the following to the application:</p>
 * <ul>
 * <li>Concrete implementation of {@link org.multibit.exchange.service.MarketService} based on the Axon Framework</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventBasedMarketService implements MarketService {

  private static Logger LOGGER = LoggerFactory.getLogger(MarketService.class);

  private final CommandGateway commandGateway;
  private final DisruptorCommandBus commandBus;
  private final EventBus eventBus;
  private CommandCallback<CreateSecurityCommand> callback = new DefaultCommandCallback();


  @Inject
  public EventBasedMarketService(
    CommandGateway commandGateway,
    DisruptorCommandBus commandBus,
    EventBus eventBus) {

    this.commandGateway = commandGateway;
    this.commandBus = commandBus;
    this.eventBus = eventBus;
  }

  @Override
  public void createSecurity(Ticker ticker, TradeableItem tradeableItem, Currency currency) {
    LOGGER.trace("Creating new security with symbol: {}", ticker.getSymbol());
    commandGateway.send(new CreateSecurityCommand(MarketId.get(), ticker, tradeableItem, currency), callback);
  }

  @Override
  public String toString() {
    return "DefaultApiService{" +
        "commandGateway=" + commandGateway +
        ", commandBus=" + commandBus +
        ", eventBus=" + eventBus +
        '}';
  }

  private class DefaultCommandCallback implements CommandCallback<CreateSecurityCommand> {
    @Override
    public void onSuccess(CreateSecurityCommand result) {
      LOGGER.debug("command success {}", result);
    }

    @Override
    public void onFailure(Throwable cause) {
      LOGGER.error("command failure {}", cause);
    }
  }
}
