package org.multibit.exchange.domainmodel;

import javax.inject.Inject;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.AggregateFactory;

/**
 * <p>Factory to provide the following to {@link org.multibit.exchange.infrastructure.service.DefaultApiService}:</p>
 * <ul>
 * <li>provision of instances of Security</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SecurityFactory implements AggregateFactory<Security> {

  private final EventBus eventBus;

  @Inject
  public SecurityFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public Security createAggregate(Object aggregateIdentifier, DomainEventMessage<?> firstEvent) {
    Security aggregate = new Security();
    aggregate.on((SecurityCreatedEvent) firstEvent.getPayload());
    AnnotationEventListenerAdapter.subscribe(aggregate, eventBus);
    return aggregate;
  }

  @Override
  public String getTypeIdentifier() {
    return Security.class.getSimpleName();
  }

  @Override
  public Class<Security> getAggregateType() {
    return Security.class;
  }
}
