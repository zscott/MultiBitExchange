package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * <p>MongoReadService to provide the following to {@link: SecuritiesReadService}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MongoReadService implements ReadService {

  private static Logger LOGGER = LoggerFactory.getLogger(MongoReadService.class);

  private final ReadModelBuilder readModelBuilder;

  private final JacksonDBCollection<SecurityReadModel, String> securities;

  private final JacksonDBCollection<OrderReadModel, String> orders;

  @Inject
  public MongoReadService(DB mongoDb, ReadModelBuilder readModelBuilder) {

    this.readModelBuilder = readModelBuilder;

    DBCollection securitiesCollection = mongoDb.getCollection(ReadModelCollections.SECURITIES);
    if (securitiesCollection == null) {
      throw new ReadServiceInitializationException("Collection is null. " +
          "Be sure your mongodb instance has a collection named: '" + ReadModelCollections.SECURITIES + "'");
    }

    DBCollection ordersCollection = mongoDb.getCollection(ReadModelCollections.ORDERS);
    if (securitiesCollection == null) {
      throw new ReadServiceInitializationException("Collection is null. " +
          "Be sure your mongodb instance has a collection named: '" + ReadModelCollections.ORDERS + "'");
    }

    try {
      this.securities = JacksonDBCollection.wrap(
          securitiesCollection,
          SecurityReadModel.class,
          String.class);
    } catch (Exception e) {
      throw new ReadServiceInitializationException("Failed to initialize collection. " +
          "Cause: " + e +
          "Be sure your mongodb instance has a collection named: '" + ReadModelCollections.SECURITIES + "'", e);
    }

    try {
      this.orders = JacksonDBCollection.wrap(
          ordersCollection,
          OrderReadModel.class,
          String.class);
    } catch (Exception e) {
      throw new ReadServiceInitializationException("Failed to initialize collection. " +
          "Cause: " + e +
          "Be sure your mongodb instance has a collection named: '" + ReadModelCollections.ORDERS + "'", e);
    }

  }

  @Override
  public List<SecurityReadModel> fetchSecurities(String exchangeId) {
    return securities.find(DBQuery.is("exchangeId", exchangeId)).toArray();
  }

  @Override
  public List<OrderReadModel> fetchOpenOrders(String tickerSymbol) {
    return orders.find(DBQuery.is("tickerSymbol", tickerSymbol)).toArray();
  }

}
