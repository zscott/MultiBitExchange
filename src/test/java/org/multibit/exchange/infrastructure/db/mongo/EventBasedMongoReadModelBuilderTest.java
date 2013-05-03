package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.eventhandling.EventBus;
import org.junit.After;
import org.junit.Before;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.EventBasedMongoReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;


import static org.mockito.Mockito.mock;

public class EventBasedMongoReadModelBuilderTest extends BaseMongoDbTest {

  private ReadModelBuilder readModelBuilder;
  private ReadService readService;
  private EventBus eventBus = mock(EventBus.class);

  @Before
  public void setUp() {
    readModelBuilder = new EventBasedMongoReadModelBuilder(db, eventBus);
    readService = new MongoReadService(db, readModelBuilder);
  }

  @After
  public void tearDown() {
    dropAllCollections(db);
  }

//  @Test
//  public void testMarketAddedEvent_validMarket() {
//    // Arrange
//    String symbol = "multibitCAD";
//    String itemSymbol = "BTC";
//    String currencySymbol = "CAD";
//    final int expectedMarketCount = 1;
//
//    // Act
//    readModelBuilder.handleEvent(new SecurityCreatedEvent(new Security(symbol, itemSymbol, currencySymbol)));
//    List<SecurityReadModel> markets = securitiesReadService.fetchMarkets();
//
//    // Assert
//    assertThat(markets).isNotNull();
//    assertThat(markets.size()).isEqualTo(expectedMarketCount);
//  }
}
