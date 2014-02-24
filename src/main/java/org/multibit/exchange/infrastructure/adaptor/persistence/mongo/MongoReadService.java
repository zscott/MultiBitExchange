package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.common.base.Preconditions;
import com.mongodb.DB;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.ReadService;

import javax.inject.Inject;
import java.util.List;

/**
 * <p>MongoReadService to provide a concrete implementation of {@link: SecuritiesReadService}.</p>
 *
 * @since 0.0.1
 *  
 */
public class MongoReadService implements ReadService {

  private JacksonDBCollection<CurrencyPairReadModel, String> securities;
  private JacksonDBCollection<OrderReadModel, String> orders;
  private DB mongoDb;

  @Inject
  public MongoReadService(DB mongoDb) {
    this.mongoDb = mongoDb;
    securities = getInitializedCollection(ReadModelCollections.SECURITIES, CurrencyPairReadModel.class);
    orders = getInitializedCollection(ReadModelCollections.ORDERS, OrderReadModel.class);
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
  public List<OrderReadModel> fetchOpenOrders(String tickerSymbol) {
    Preconditions.checkState(orders != null, "orders collection must be initialized");
    return orders.find(DBQuery.is("tickerSymbol", tickerSymbol)).toArray();
  }
}
