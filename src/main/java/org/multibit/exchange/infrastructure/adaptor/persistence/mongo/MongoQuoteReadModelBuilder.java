package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TickerRemovedEvent;
import org.multibit.exchange.domain.event.TopOrderCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Builds a read-only representation of each symbol's quote details.</p>
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
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getSymbol();
    QuoteReadModel quoteReadModel = new QuoteReadModel(exchangeId, tickerSymbol);
    repository.upsert(quoteReadModel);
  }

  @EventHandler
  public void handle(TickerRemovedEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getTickerSymbol();
    repository.deleteByExchangeAndTicker(exchangeId, tickerSymbol);
  }

  @EventHandler
  public void handle(LimitOrderAddedToExistingPriceLevelEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getOrder().getTicker().getSymbol();
    QuoteReadModel readModel = repository.findByExchangeAndTicker(exchangeId, tickerSymbol);
    readModel.getBook(event.getOrder().getSide());
  }

  @EventHandler
  public void handle(LimitOrderAddedToNewPriceLevelEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getOrder().getTicker().getSymbol();
    repository.deleteByExchangeAndTicker(exchangeId, tickerSymbol);
  }

  @EventHandler
  public void handle(PriceLevelCompletelyFilledEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getTrade().getTicker().getSymbol();
    repository.deleteByExchangeAndTicker(exchangeId, tickerSymbol);
  }

  @EventHandler
  public void handle(TopOrderCompletelyFilledEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getTrade().getTicker().getSymbol();
    repository.deleteByExchangeAndTicker(exchangeId, tickerSymbol);
  }

  @EventHandler
  public void handle(TopOrderPartiallyFilledEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String tickerSymbol = event.getTrade().getTicker().getSymbol();
    repository.deleteByExchangeAndTicker(exchangeId, tickerSymbol);
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
        qc.getExchangeId().getIdentifier(),
        qc.getTicker().getSymbol(), new OrderBookReadModel(Side.BUY), new OrderBookReadModel(Side.SELL)
    ));
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
