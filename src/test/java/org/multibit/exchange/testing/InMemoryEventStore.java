package org.multibit.exchange.testing;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.eventstore.EventStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: ZS - Contribute this to Axonframework.
 */

/**
 * <p>In-memory {@link EventStore} implementation for testing.</p>
 *
 * @since 0.0.1
 */
public class InMemoryEventStore implements EventStore {

  private Map<String, Map<Object, List<DomainEventMessage>>> aggTypeMap;

  public InMemoryEventStore() {
    aggTypeMap = new HashMap<>();
  }

  @Override
  public void appendEvents(String type, DomainEventStream eventsToStore) {
    if (!eventsToStore.hasNext()) {
      return;
    }

    DomainEventMessage next = eventsToStore.next();
    Object aggregateIdentifier = next.getAggregateIdentifier();
    List<DomainEventMessage> eventList = findOrCreateEventList(type, aggregateIdentifier);

    do {
      assert (next.getAggregateIdentifier().equals(aggregateIdentifier));
      eventList.add(next);
      if (eventsToStore.hasNext()) {
        next = eventsToStore.next();
      } else {
        next = null;
      }
    } while (next != null);

  }

  private List<DomainEventMessage> findOrCreateEventList(String type, Object aggregateIdentifier) {
    List<DomainEventMessage> eventMessageList = findEventList(type, aggregateIdentifier);
    if (eventMessageList == null) {
      createEventList(type, aggregateIdentifier);
    }
    return aggTypeMap.get(type).get(aggregateIdentifier);
  }

  private List<DomainEventMessage> findEventList(String type, Object aggregateIdentifier) {
    if (!aggTypeMap.containsKey(type)) {
      return null;
    }

    Map<Object, List<DomainEventMessage>> aggIdentifierToEventsMap = aggTypeMap.get(type);
    if (!aggIdentifierToEventsMap.containsKey(aggregateIdentifier)) {
      return null;
    }

    List<DomainEventMessage> eventList = aggIdentifierToEventsMap.get(aggregateIdentifier);
    return eventList;
  }

  private void createEventList(String type, Object aggregateIdentifier) {
    if (!aggTypeMap.containsKey(type)) {
      Map<Object, List<DomainEventMessage>> instanceToEventListMap = new HashMap<>();
      aggTypeMap.put(type, instanceToEventListMap);
    }

    if (!aggTypeMap.get(type).containsKey(aggregateIdentifier)) {
      List<DomainEventMessage> eventList = Lists.newLinkedList();
      aggTypeMap.get(type).put(aggregateIdentifier, eventList);
    }
  }

  @Override
  public DomainEventStream readEvents(String type, Object aggregateIdentifier) {
    List<DomainEventMessage> eventList = findEventList(type, aggregateIdentifier);
    if (eventList == null) {
      return new InMemoryEventStream();
    }
    return new InMemoryEventStream(eventList);
  }

  private class InMemoryEventStream implements DomainEventStream {

    private final PeekingIterator<DomainEventMessage> iterator;

    public InMemoryEventStream(List<DomainEventMessage> eventList) {
      iterator = Iterators.peekingIterator(eventList.iterator());
    }

    public InMemoryEventStream(DomainEventMessage... events) {
      iterator = Iterators.peekingIterator(Lists.newArrayList(events).iterator());
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public DomainEventMessage next() {
      return iterator.next();
    }

    @Override
    public DomainEventMessage peek() {
      return iterator.peek();
    }
  }
}
