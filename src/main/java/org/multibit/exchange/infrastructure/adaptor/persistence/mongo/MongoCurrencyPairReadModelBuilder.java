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
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ModelBuilder to provide the following to the {@link org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.ReadModelBuilder}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class MongoCurrencyPairReadModelBuilder
    extends BaseMongoRepository<CurrencyPairReadModel, String> {

  static Logger LOGGER = LoggerFactory.getLogger(MongoCurrencyPairReadModelBuilder.class);

  private final EventBus eventBus;

  @Inject
  public MongoCurrencyPairReadModelBuilder(DB mongoDb, EventBus eventBus) {
    super(mongoDb, JacksonDBCollection.wrap(
        mongoDb.getCollection(ReadModelCollections.SECURITIES),
        CurrencyPairReadModel.class,
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
        new CurrencyPairReadModel(
            newId(),
            event.getExchangeId().getIdentifier(),
            currencyPair.getTicker().getSymbol(),
            currencyPair.getBaseCurrency().getSymbol(),
            currencyPair.getCounterCurrency().getSymbol()));
  }

  private String newId() {
    return ObjectId.get().toString();
  }
}
