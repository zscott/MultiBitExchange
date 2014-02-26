package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
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
public class MongoQuoteReadModelBuilder
        extends BaseMongoRepository<QuoteReadModel, String> {

  /*
   * The initial quote price - when a new CurrencyPair is registered.
   */
  public static final ItemPrice INITIAL_PRICE = new ItemPrice("0");

  static Logger LOGGER = LoggerFactory.getLogger(MongoQuoteReadModelBuilder.class);

  private final EventBus eventBus;

  @Inject
  public MongoQuoteReadModelBuilder(DB mongoDb, EventBus eventBus) {
    super(mongoDb, JacksonDBCollection.wrap(
            mongoDb.getCollection(ReadModelCollections.QUOTES),
            QuoteReadModel.class,
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
            new QuoteReadModel(
                    newId(),
                    event.getExchangeId().getCode(),
                    currencyPair.getTicker().getSymbol(),
                    INITIAL_PRICE.getRaw(),
                    INITIAL_PRICE.getRaw(),
                    createFormattedTimestamp()));
  }

  private String createFormattedTimestamp() {
    return DateUtils.formatISO8601(DateUtils.nowUtc());
  }

  private String newId() {
    return ObjectId.get().toString();
  }
}
