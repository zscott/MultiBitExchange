package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ModelBuilder to provide the following to the {@link org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventBasedMongoReadModelBuilder extends BaseMongoRepository<SecurityReadModel, String> implements ReadModelBuilder {

  static Logger LOGGER = LoggerFactory.getLogger(EventBasedMongoReadModelBuilder.class);

  private final EventBus eventBus;

  @Inject
  public EventBasedMongoReadModelBuilder(DB mongoDb, EventBus eventBus) {
    super(mongoDb, JacksonDBCollection.wrap(
        mongoDb.getCollection(ReadModelCollections.SECURITIES),
        SecurityReadModel.class,
        String.class));
    this.eventBus = eventBus;

    LOGGER.debug("subscribing to events on {}", eventBus);
    AnnotationEventListenerAdapter.subscribe(this, eventBus);
  }

  @EventHandler
  public void handleSecurityCreated(SecurityCreatedEvent event) {
    LOGGER.debug("handling SecurityCreatedEvent: {}", event);
    super.create(new SecurityReadModel(
        newId(),
        event.getExchangeId().getName(),
        event.getTickerSymbol(),
        event.getTradeableItemSymbol(),
        event.getCurrencySymbol()));
  }

  private String newId() {
    return ObjectId.get().toString();
  }

  @Override
  public String toString() {
    return "EventBasedMongoReadModelBuilder{" +
        "eventBus=" + eventBus +
        '}';
  }
}
