package org.multibit.exchange.infrastructure.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;

import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.domain.market.MarketEvent;
import org.multibit.exchange.readmodel.MarketReadModel;
import org.multibit.exchange.readmodel.MarketReadModelBuilder;

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
    Market market = marketEvent.getMarket();
    super.create(new MarketReadModel(newId(), market.getSymbol(), market.getItemSymbol(), market.getCurrencySymbol()));
  }

  private String newId() {
    return ObjectId.get().toString();
  }
}
