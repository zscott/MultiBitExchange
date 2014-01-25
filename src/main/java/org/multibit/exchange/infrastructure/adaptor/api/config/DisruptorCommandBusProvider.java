package org.multibit.exchange.infrastructure.adaptor.api.config;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.axonframework.commandhandling.annotation.AnnotationCommandTargetResolver;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.disruptor.DisruptorConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventstore.EventStore;

import javax.inject.Inject;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Instance of {@link DisruptorCommandBus}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Singleton
public class DisruptorCommandBusProvider implements Provider<DisruptorCommandBus> {

  private final EventStore eventStore;
  private final EventBus eventBus;

  @Inject
  public DisruptorCommandBusProvider(EventStore eventStore, EventBus eventBus) {
    this.eventStore = eventStore;
    this.eventBus = eventBus;
  }

  @Override
  public DisruptorCommandBus get() {
    DisruptorConfiguration configuration = new DisruptorConfiguration();
    configuration.setCommandTargetResolver(new AnnotationCommandTargetResolver());
    return new DisruptorCommandBus(eventStore, eventBus, configuration);

  }
}
