package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.multibit.common.jackson.PriceVolume;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.command.OrderDescriptor;
import org.multibit.exchange.domain.command.SecurityOrderFactory;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.TickerRegisteredEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoMarketDepthPresentationModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQueryProcessor;
import org.multibit.exchange.presentation.model.marketdepth.AskDepthData;
import org.multibit.exchange.presentation.model.marketdepth.BidDepthData;
import org.multibit.exchange.presentation.model.marketdepth.DepthDataAsserts;
import org.multibit.exchange.presentation.model.marketdepth.MarketDepthPresentationModel;
import org.multibit.exchange.service.QueryProcessor;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.OrderDescriptorFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class MongoMarketDepthPresentationModelBuilderTest extends BaseMongoDbTest {

  private MongoMarketDepthPresentationModelBuilder modelBuilder;

  private QueryProcessor queryProcessor;
  private EventBus eventBus = new SimpleEventBus();
  private ExchangeId exchangeId;
  private CurrencyPair currencyPair;
  private Ticker ticker;

  @Before
  public void setUp() {
    exchangeId = ExchangeIdFaker.createValid();
    currencyPair = CurrencyPairFaker.createValid();
    ticker = currencyPair.getTicker();
    queryProcessor = new MongoQueryProcessor(db);
    modelBuilder = new MongoMarketDepthPresentationModelBuilder(db, eventBus, queryProcessor);
  }

  @After
  public void tearDown() {
    dropAllCollections(db);
  }

  @Test
  public void fetchMarketDepth_givenCurrencyPairRegistered() {
    // Arrange
    TickerRegisteredEvent event = new TickerRegisteredEvent(exchangeId, ticker);
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event));

    // Act
    MarketDepthPresentationModel model
        = queryProcessor.fetchMarketDepth(exchangeId.getIdentifier(), ticker.getSymbol());

    // Assert
    assertThat(model).isNotNull();
  }

  @Test
  public void fetchMarketDepth_scenario() {
    // Arrange
    publishCurrencyPairRegistered();
    publishBuyLimitOrder("10", "10.10");
    publishBuyLimitOrder("10", "17.31");
    publishBuyLimitOrder("10", "12.05");
    publishBuyLimitOrder("11", "100.00");


    publishSellLimitOrder("12", "10");
    publishSellLimitOrder("13", "10");
    publishSellLimitOrder("13", "10");
    publishSellLimitOrder("13", "10");
    publishSellLimitOrder("13", "10");
    publishSellLimitOrder("17", "10");

    // Act
    MarketDepthPresentationModel model
        = queryProcessor.fetchMarketDepth(exchangeId.getIdentifier(), ticker.getSymbol());

    // Assert
    assertThat(model).isNotNull();
    BidDepthData bidDepthData = model.getBidDepthData();
    AskDepthData askDepthData = model.getAskDepthData();

    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
        bidDepthData,
        new PriceVolume("11", "100"),
        new PriceVolume("10", "39.46")
    );
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
        askDepthData,
        new PriceVolume("12", "10"),
        new PriceVolume("13", "40"),
        new PriceVolume("17", "10")
    );
  }

  private void publishSellLimitOrder(String price, String qty) {
    LimitOrder sellOrder = createSellLimitOrder(price, qty);
    LimitOrderAddedToNewPriceLevelEvent e3 = new LimitOrderAddedToNewPriceLevelEvent(exchangeId, sellOrder, sellOrder.getLimitPrice());
    eventBus.publish(GenericDomainEventMessage.asEventMessage(e3));
  }

  private LimitOrder createSellLimitOrder(String price, String qty) {
    OrderDescriptor orderDescriptor = OrderDescriptorFaker.createValidLimitOrder()
        .withPrice(price)
        .withQty(qty)
        .withSide("Sell")
        .withTicker(ticker.getSymbol());

    return (LimitOrder) SecurityOrderFactory.createOrderFromDescriptor(orderDescriptor);
  }

  private void publishCurrencyPairRegistered() {
    TickerRegisteredEvent event = new TickerRegisteredEvent(exchangeId, ticker);
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event));
  }

  private void publishBuyLimitOrder(String price, String qty) {
    LimitOrder buyOrder = createBuyLimitOrder(price, qty);
    LimitOrderAddedToNewPriceLevelEvent e2 = new LimitOrderAddedToNewPriceLevelEvent(exchangeId, buyOrder, buyOrder.getLimitPrice());
    eventBus.publish(GenericDomainEventMessage.asEventMessage(e2));
  }

  private LimitOrder createBuyLimitOrder(String price, String qty) {
    OrderDescriptor orderDescriptor = OrderDescriptorFaker.createValidLimitOrder()
        .withPrice(price)
        .withQty(qty)
        .withSide("Buy")
        .withTicker(ticker.getSymbol());

    return (LimitOrder) SecurityOrderFactory.createOrderFromDescriptor(orderDescriptor);
  }
}
