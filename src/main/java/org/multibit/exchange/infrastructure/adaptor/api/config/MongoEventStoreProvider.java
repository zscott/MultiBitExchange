package org.multibit.exchange.infrastructure.adaptor.api.config;

import com.google.inject.Provider;
import com.mongodb.Mongo;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.eventstore.mongo.MongoTemplate;

import javax.inject.Inject;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Instance of an EventStore backed by MongoDB</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MongoEventStoreProvider implements Provider<EventStore> {
  private final MongoTemplate mongoTemplate;

  @Inject
  public MongoEventStoreProvider(Mongo mongo) {
    mongoTemplate = new DefaultMongoTemplate(mongo);
  }

  @Override
  public EventStore get() {
    return new MongoEventStore(mongoTemplate);
  }
}
