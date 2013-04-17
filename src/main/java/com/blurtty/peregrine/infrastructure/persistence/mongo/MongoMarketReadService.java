package com.blurtty.peregrine.infrastructure.persistence.mongo;

import com.mongodb.DB;
import java.util.List;
import javax.inject.Inject;
import org.mongojack.JacksonDBCollection;

import com.blurtty.peregrine.service.MarketReadService;

import com.blurtty.peregrine.readmodel.MarketReadModel;

/**
 * <p>MongoReadService to provide the following to {@link: MarketReadService}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MongoMarketReadService implements MarketReadService {

  private final DB db;
  private final JacksonDBCollection<MarketReadModel, String> collection;

  @Inject
  public MongoMarketReadService(DB mongoDb) {
    this.db = mongoDb;
    this.collection = JacksonDBCollection.wrap(
        mongoDb.getCollection(MongoCollections.MARKET_READ_COLLECTION),
        MarketReadModel.class,
        String.class);
  }

  @Override
  public List<MarketReadModel> fetchMarkets() {
    return collection.find().toArray();
  }
}
