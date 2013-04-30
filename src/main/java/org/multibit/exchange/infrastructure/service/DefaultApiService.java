package org.multibit.exchange.infrastructure.service;

import javax.inject.Inject;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.domainmodel.CreateSecurityCommand;
import org.multibit.exchange.domainmodel.SecurityId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Service to provide the following to the application:</p>
 * <ul>
 * <li>Concrete implementation of {@link ApiService} based on Axon Framework</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DefaultApiService implements ApiService {

  private static Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

  private final CommandGateway commandGateway;
  private final DisruptorCommandBus commandBus;
  private final EventBus eventBus;
  private CommandCallback<CreateSecurityCommand> callback = new DefaultCommandCallback();

  @Inject
  public DefaultApiService(CommandGateway commandGateway, DisruptorCommandBus commandBus, EventBus eventBus) {
    LOGGER.trace("Initialized with\n{}\n{}\n{}",
        commandGateway,
        commandBus,
        eventBus);
    this.commandGateway = commandGateway;
    this.commandBus = commandBus;
    this.eventBus = eventBus;
  }

  @Override
  public void createSecurity(String tickerSymbol, String tradeableItemSymbol, String currencySymbol) {
    LOGGER.trace("Creating new security with symbol: {}", tickerSymbol);
    commandGateway.send(new CreateSecurityCommand(SecurityId.next(), new Ticker(tickerSymbol), new org.multibit.exchange.domainmodel.TradeableItem(tradeableItemSymbol), currencySymbol), callback);
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
