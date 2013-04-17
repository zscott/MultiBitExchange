package com.blurtty.peregrine.infrastructure.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.mongojack.JacksonDBCollection;

import com.blurtty.peregrine.domain.market.MarketEvent;
import com.blurtty.peregrine.readmodel.MarketReadModel;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;

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

  // todo - handle different types of MarketEvents
  @Override
  public void handleMarketEvent(MarketEvent marketEvent) {
    super.create(MarketReadModel.fromMarket(marketEvent.getMarket()));
  }
}
