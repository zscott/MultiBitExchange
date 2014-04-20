package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mongodb.DB;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.multibit.exchange.presentation.model.marketdepth.MarketDepthPresentationModel;
import org.multibit.exchange.service.QueryProcessor;

import javax.inject.Inject;
import java.util.List;

/**
 * <p>MongoReadService to provide a concrete implementation of {@link: SecuritiesReadService}.</p>
 *
 * @since 0.0.1
 *  
 */
public class MongoQueryProcessor implements QueryProcessor {

  private DB mongoDb;

  private final JacksonDBCollection<CurrencyPairReadModel, String> securities;

  private final JacksonDBCollection<QuoteReadModel, String> quotes;

  private final JacksonDBCollection<OrderBookReadModel, String> orderBooks;

  private final JacksonDBCollection<MarketDepthPresentationModel, String> marketDepth;

  @Inject
  public MongoQueryProcessor(DB mongoDb) {
    this.mongoDb = mongoDb;
    securities = getInitializedCollection(ReadModelCollections.SECURITIES, CurrencyPairReadModel.class);
    quotes = getInitializedCollection(ReadModelCollections.QUOTES, QuoteReadModel.class);
    orderBooks = getInitializedCollection(ReadModelCollections.ORDER_BOOKS, OrderBookReadModel.class);
    marketDepth = getInitializedCollection(ReadModelCollections.MARKET_DEPTH, MarketDepthPresentationModel.class);
  }

  private <T> JacksonDBCollection<T, String> getInitializedCollection(String collectionName, Class<T> collectionType) {
    return MongoUtils.getInitializedJacksonCollection(mongoDb, collectionName, collectionType, String.class);
  }

  @Override
  public List<CurrencyPairReadModel> fetchSecurities(String exchangeId) {
    Preconditions.checkState(securities != null, "securities collection must be initialized");
    return securities.find(DBQuery.is("exchangeId", exchangeId)).toArray();
  }

  @Override
  public List<QuoteReadModel> fetchQuotes(String exchangeId) {
    Preconditions.checkState(quotes != null, "quotes collection must be initialized");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(exchangeId), "exchangeId must not be null or empty");
    return quotes.find(DBQuery.is("exchangeId", exchangeId)).toArray();
  }

  @Override
  public OrderBookReadModel fetchOrderBook(String exchangeId, String tickerSymbol) {
    return orderBooks.findOne(withExchangeIdAndTickerSymbol(exchangeId, tickerSymbol));
  }

  @Override
  public MarketDepthPresentationModel fetchMarketDepth(String exchangeId, CurrencyPairId currencyPairId) {
    return marketDepth.findOne(withExchangeIdAndTickerSymbol(exchangeId, currencyPairId.getIdentifier()));
  }

  private DBQuery.Query withExchangeIdAndTickerSymbol(String exchangeId, String tickerSymbol) {
    return DBQuery.is("exchangeId", exchangeId).and(DBQuery.is("currencyPairId", tickerSymbol));
  }
}
