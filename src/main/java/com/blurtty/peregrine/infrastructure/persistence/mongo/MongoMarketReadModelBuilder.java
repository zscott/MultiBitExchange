package com.blurtty.peregrine.infrastructure.persistence.mongo;

import com.blurtty.peregrine.domain.MarketAddedEvent;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;
import com.blurtty.peregrine.readmodel.MarketReadModel;
import com.google.inject.Inject;
import com.mongodb.DB;
import org.mongojack.JacksonDBCollection;

/**
 * <p>ModelBuilder to provide the following to the {@link: MarketReadModelBuilder}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MongoMarketReadModelBuilder extends BaseMongoRepository<MarketReadModel, String> implements MarketReadModelBuilder {

  @Inject
  public MongoMarketReadModelBuilder(DB mongoDb) {
    super(mongoDb, JacksonDBCollection.wrap(
        mongoDb.getCollection(MongoCollections.MARKET_READ_COLLECTION),
        MarketReadModel.class,
        String.class));
  }

  @Override
  public void handleMarketAddedEvent(MarketAddedEvent marketAddedEvent) {
    super.create(MarketReadModel.fromMarket(marketAddedEvent.getMarket()));
  }
}
