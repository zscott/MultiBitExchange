package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.DB;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.domain.event.LimitOrderAddedEvent;
import org.multibit.exchange.domain.event.TickerRegisteredEvent;
import org.multibit.exchange.domain.event.TradeExecutedEvent;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.presentation.model.marketdepth.DepthData;
import org.multibit.exchange.presentation.model.marketdepth.MarketDepthPresentationModel;
import org.multibit.exchange.service.QueryProcessor;

public class MongoMarketDepthPresentationModelBuilder
    extends BaseMongoRepository<MarketDepthPresentationModel, String> {

  private QueryProcessor queryProcessor;

  @Inject
  public MongoMarketDepthPresentationModelBuilder(DB mongoDb, EventBus eventBus, QueryProcessor queryProcessor) {
    super(mongoDb, JacksonDBCollection.wrap(
        mongoDb.getCollection(ReadModelCollections.MARKET_DEPTH),
        MarketDepthPresentationModel.class,
        String.class));
    this.queryProcessor = queryProcessor;
    AnnotationEventListenerAdapter.subscribe(this, eventBus);
  }

  @EventHandler
  public void handle(TickerRegisteredEvent event) {
    MarketDepthPresentationModel model = new MarketDepthPresentationModel(
        new ObjectId().toString(),
        event.getExchangeId().getIdentifier(),
        event.getTicker().getSymbol());
    super.save(model);
  }

  @EventHandler
  public void handle(LimitOrderAddedEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String ticker = event.getOrder().getTicker().getSymbol();
    MarketDepthPresentationModel model = queryProcessor.fetchMarketDepth(exchangeId, ticker);

    Side side = event.getOrder().getSide();
    String price = event.getOrder().getLimitPrice().getRaw();
    String volumeToIncreaseBy = event.getOrder().getUnfilledQuantity().getRaw();
    DepthData depthData = getDepthData(model, side);
    depthData.increaseVolumeAtPrice(price, volumeToIncreaseBy);
    super.save(model);
  }

  @EventHandler
  public void handle(TradeExecutedEvent event) {
    String exchangeId = event.getExchangeId().getIdentifier();
    String ticker = event.getTrade().getTicker().getSymbol();
    MarketDepthPresentationModel model = queryProcessor.fetchMarketDepth(exchangeId, ticker);

    Side side = event.getSide();
    String price = event.getTrade().getPrice().getRaw();
    String volumeToDecreaseBy = event.getTrade().getQuantity().getRaw();
    DepthData depthData = getDepthData(model, side);
    depthData.decreaseVolumeAtPrice(price, volumeToDecreaseBy);
    super.save(model);
  }

  private DepthData getDepthData(MarketDepthPresentationModel model, Side side) {
    return (side == Side.BUY) ? model.getBidDepthData() : model.getAskDepthData();
  }

}
