package org.multibit.exchange.infrastructure.adaptor.api.config;

import com.google.inject.Provider;
import javax.inject.Inject;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.multibit.exchange.infrastructure.service.DefaultApiService;
import org.multibit.exchange.service.ApiService;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class ApiServiceProvider implements Provider<ApiService> {
  private final DisruptorCommandBus commandBus;
  private final CommandGateway commandGateway;
  private final EventBus eventBus;

  @Inject
  public ApiServiceProvider(DisruptorCommandBus commandBus, CommandGateway commandGateway, EventBus eventBus) {
    this.commandBus = commandBus;
    this.commandGateway = commandGateway;
    this.eventBus = eventBus;
  }

  @Override
  public ApiService get() {
    return new DefaultApiService(commandGateway, commandBus, eventBus);
  }
}
