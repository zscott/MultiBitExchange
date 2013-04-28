package org.multibit.exchange.infrastructure.adaptor.api.config;

import javax.inject.Inject;
import javax.inject.Provider;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandTargetResolver;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.disruptor.DisruptorConfiguration;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.multibit.exchange.infrastructure.adaptor.axon.EventSourcedSecurity;
import org.multibit.exchange.infrastructure.adaptor.axon.EventSourcedSecurityAggregateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Instance of CommandGateway</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DefaultCommandGatewayProvider implements Provider<DefaultCommandGateway> {

  private static Logger LOGGER = LoggerFactory.getLogger(DefaultCommandGatewayProvider.class);

  private final DisruptorCommandBus commandBus;
  private final EventStore eventStore;
  private final EventBus eventBus;
  private final Repository<EventSourcedSecurity> repository;

  @Inject
  public DefaultCommandGatewayProvider(EventStore eventStore, EventBus eventBus) {
    this.eventStore = eventStore;
    this.eventBus = eventBus;

    DisruptorConfiguration configuration = new DisruptorConfiguration();
    configuration.setCommandTargetResolver(new AnnotationCommandTargetResolver());

    commandBus = new DisruptorCommandBus(eventStore, eventBus, configuration);
    repository = commandBus.createRepository(new EventSourcedSecurityAggregateFactory(eventBus));

    registerCommandHandlers();
  }

  private void registerCommandHandlers() {
    LOGGER.debug("** SUBSCRIBING ** aggregate {} to repository {} and commandBus {}",
        EventSourcedSecurity.class,
        repository,
        commandBus);
    AggregateAnnotationCommandHandler.subscribe(EventSourcedSecurity.class, repository, commandBus);
  }

  @Override
  public DefaultCommandGateway get() {
    return new DefaultCommandGateway(commandBus);
  }
}
