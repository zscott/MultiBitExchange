package org.multibit.exchange.infrastructure.adaptor.api.config;

import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandTargetResolver;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.disruptor.DisruptorConfiguration;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.multibit.exchange.infrastructure.adaptor.events.ExchangeAggregateRoot;

import javax.inject.Inject;
import javax.inject.Provider;

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

  private final DisruptorCommandBus commandBus;

  private final Repository<ExchangeAggregateRoot> repository;

  @Inject
  public DefaultCommandGatewayProvider(EventStore eventStore, EventBus eventBus) {
    DisruptorConfiguration configuration = new DisruptorConfiguration();
    configuration.setCommandTargetResolver(new AnnotationCommandTargetResolver());

    commandBus = new DisruptorCommandBus(eventStore, eventBus, configuration);
    repository = commandBus.createRepository(new GenericAggregateFactory(ExchangeAggregateRoot.class));

    registerCommandHandlers();
  }

  private void registerCommandHandlers() {
    AggregateAnnotationCommandHandler.subscribe(ExchangeAggregateRoot.class, repository, commandBus);
  }

  @Override
  public DefaultCommandGateway get() {
    return new DefaultCommandGateway(commandBus);
  }
}
