package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.domain.SecurityCreatedEvent;
import org.multibit.exchange.readmodel.SecuritiesReadModelBuilder;
import org.multibit.exchange.readmodel.SecurityReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ModelBuilder to provide the following to the {@link org.multibit.exchange.readmodel.SecuritiesReadModelBuilder}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventBasedMongoSecuritiesReadModelBuilder extends BaseMongoRepository<SecurityReadModel, String> implements SecuritiesReadModelBuilder {

  static Logger LOGGER = LoggerFactory.getLogger(EventBasedMongoSecuritiesReadModelBuilder.class);

  private final EventBus eventBus;

  @Inject
  public EventBasedMongoSecuritiesReadModelBuilder(DB mongoDb, EventBus eventBus) {
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
        event.getTickerSymbol(),
        event.getTradeableItemSymbol(),
        event.getCurrencySymbol()));
  }

  private String newId() {
    return ObjectId.get().toString();
  }

  @Override
  public String toString() {
    return "EventBasedMongoSecuritiesReadModelBuilder{" +
        "eventBus=" + eventBus +
        '}';
  }
}
