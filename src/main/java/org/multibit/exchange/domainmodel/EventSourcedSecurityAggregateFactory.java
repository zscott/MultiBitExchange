package org.multibit.exchange.domainmodel;

import javax.inject.Inject;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.AggregateFactory;

/**
 * <p>Factory to provide the following to {@link org.multibit.exchange.infrastructure.service.DefaultApiService}:</p>
 * <ul>
 * <li>provision of instances of EventSourcedSecurity</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventSourcedSecurityAggregateFactory implements AggregateFactory<EventSourcedSecurity> {

  private final EventBus eventBus;

  @Inject
  public EventSourcedSecurityAggregateFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public EventSourcedSecurity createAggregate(Object aggregateIdentifier, DomainEventMessage<?> firstEvent) {
    EventSourcedSecurity aggregate = new EventSourcedSecurity();
    aggregate.on((SecurityCreatedEvent) firstEvent.getPayload());
    AnnotationEventListenerAdapter.subscribe(aggregate, eventBus);
    return aggregate;
  }

  @Override
  public String getTypeIdentifier() {
    return EventSourcedSecurity.class.getSimpleName();
  }

  @Override
  public Class<EventSourcedSecurity> getAggregateType() {
    return EventSourcedSecurity.class;
  }
}
