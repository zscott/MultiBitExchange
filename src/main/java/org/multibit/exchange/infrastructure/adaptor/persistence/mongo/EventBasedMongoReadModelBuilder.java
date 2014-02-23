package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
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
  public void handle(CurrencyPairRegisteredEvent event) {
    LOGGER.debug("handling CurrencyPairRegisteredEvent: {}", event);

    CurrencyPair currencyPair = event.getCurrencyPair();
    super.create(
        new SecurityReadModel(
            newId(),
        event.getExchangeId().getCode(),
        currencyPair.getTicker().getSymbol(),
        currencyPair.getBaseCurrency().getSymbol(),
        currencyPair.getCounterCurrency().getSymbol()));
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
