package org.multibit.exchange.infrastructure.adaptor.api.config;

import com.google.inject.Provider;
import javax.inject.Inject;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.infrastructure.service.EventBasedExchangeService;
import org.multibit.exchange.service.ExchangeService;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Creation of concrete of DefaultApiService</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventBasedExchangeServiceProvider implements Provider<ExchangeService> {
  private final DisruptorCommandBus commandBus;
  private final CommandGateway commandGateway;
  private final EventBus eventBus;

  @Inject
  public EventBasedExchangeServiceProvider(
      DisruptorCommandBus commandBus,
      CommandGateway commandGateway,
      EventBus eventBus) {

    this.commandBus = commandBus;
    this.commandGateway = commandGateway;
    this.eventBus = eventBus;
  }

  @Override
  public ExchangeService get() {
    return new EventBasedExchangeService(commandGateway, commandBus, eventBus);
  }
}
