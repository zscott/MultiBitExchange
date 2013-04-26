package org.multibit.exchange.infrastructure.db.mongo;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.domain.event.MarketAddedEvent;
import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoMarketReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoMarketReadService;
import org.multibit.exchange.readmodel.MarketReadModel;
import org.multibit.exchange.readmodel.MarketReadModelBuilder;
import org.multibit.exchange.service.MarketReadService;


import static org.fest.assertions.api.Assertions.assertThat;

public class MongoMarketReadModelBuilderTest extends BaseMongoDbTest {

  private MarketReadModelBuilder readModelBuilder;
  private MarketReadService marketReadService;

  @Before
  public void setUp() {
    readModelBuilder = new MongoMarketReadModelBuilder(db);
    marketReadService = new MongoMarketReadService(db);
  }

  @After
  public void tearDown() {
    dropAllCollections(db);
  }

  @Test
  public void testMarketAddedEvent_validMarket() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final int expectedMarketCount = 1;

    // Act
    readModelBuilder.handleEvent(new MarketAddedEvent(new Market(symbol, itemSymbol, currencySymbol)));
    List<MarketReadModel> markets = marketReadService.fetchMarkets();

    // Assert
    assertThat(markets).isNotNull();
    assertThat(markets.size()).isEqualTo(expectedMarketCount);
  }
}
