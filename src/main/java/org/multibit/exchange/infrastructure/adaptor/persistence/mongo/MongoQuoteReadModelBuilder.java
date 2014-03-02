package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ReadModelBuilder to build a structural representation of a single ticker symbol's publicly viewable order book.</p>
 *
 * @since 0.0.1
 *  
 */
public class MongoQuoteReadModelBuilder {

  /*
   * The initial quote price - when a new CurrencyPair is registered.
   */
  public static final ItemPrice INITIAL_PRICE = new ItemPrice("0");

  static Logger LOGGER = LoggerFactory.getLogger(MongoQuoteReadModelBuilder.class);

  private final MongoQuoteReadModelRepository repository;

  @Inject
  public MongoQuoteReadModelBuilder(DB mongoDb, EventBus eventBus) {
    repository = new MongoQuoteReadModelRepository(mongoDb);

    LOGGER.debug("subscribing to events on {}", eventBus);
    AnnotationEventListenerAdapter.subscribe(this, eventBus);
  }

  @EventHandler
  public void handle(CurrencyPairRegisteredEvent event) {
    LOGGER.debug("handling CurrencyPairRegisteredEvent: {}", event);
    QuoteCalculator quoteCalculator = initializeQuoteCalculator(event.getExchangeId(), event.getCurrencyPair().getTicker());
    persistQuote(quoteCalculator);
  }

  private void persistQuote(QuoteCalculator qc) {
//    repository.upsert(new QuoteReadModel(
//        qc.getExchangeId(),
//        qc.getTickerSymbol(),
//        qc.getBid(),
//        qc.getAsk(),
//        qc.getSpread(),
//        createFormattedTimestamp()));

    repository.upsert(new QuoteReadModel(
        newId(),
        qc.getExchangeId().getCode(),
        qc.getTicker().getSymbol(),
        INITIAL_PRICE.getRaw(),
        INITIAL_PRICE.getRaw(),
        createFormattedTimestamp()));
  }

  private QuoteCalculator initializeQuoteCalculator(ExchangeId exchangeId, Ticker ticker) {
    return new QuoteCalculator(exchangeId, ticker);
  }

  private String createFormattedTimestamp() {
    return DateUtils.formatISO8601(DateUtils.nowUtc());
  }

  private String newId() {
    return ObjectId.get().toString();
  }

  private class QuoteCalculator {
    private final ExchangeId exchangeId;
    private final Ticker ticker;

    public QuoteCalculator(ExchangeId exchangeId, Ticker ticker) {
      this.exchangeId = exchangeId;
      this.ticker = ticker;
    }

    public ExchangeId getExchangeId() {
      return exchangeId;
    }

    public Ticker getTicker() {
      return ticker;
    }
  }
}
