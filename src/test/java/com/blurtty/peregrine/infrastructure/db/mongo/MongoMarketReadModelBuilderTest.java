package com.blurtty.peregrine.infrastructure.db.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadModelBuilder;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadService;

import com.blurtty.peregrine.service.MarketReadService;

import com.blurtty.peregrine.domain.market.Market;
import com.blurtty.peregrine.domain.market.MarketAddedEvent;
import com.blurtty.peregrine.readmodel.MarketReadModel;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;


import java.util.List;

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
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final int expectedMarketCount = 1;

    // Act
    readModelBuilder.handleMarketEvent(new MarketAddedEvent(new Market(symbol, itemSymbol, currencySymbol)));
    List<MarketReadModel> markets = marketReadService.fetchMarkets();

    // Assert
    assertThat(markets).isNotNull();
    assertThat(markets.size()).isEqualTo(expectedMarketCount);
  }
}
