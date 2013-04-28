package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.eventhandling.EventBus;
import org.junit.After;
import org.junit.Before;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.EventBasedMongoSecuritiesReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoSecuritiesReadService;
import org.multibit.exchange.readmodel.SecuritiesReadModelBuilder;
import org.multibit.exchange.service.SecuritiesReadService;


import static org.mockito.Mockito.mock;

public class MongoMarketReadModelBuilderTest extends BaseMongoDbTest {

  private SecuritiesReadModelBuilder readModelBuilder;
  private SecuritiesReadService securitiesReadService;
  private EventBus eventBus = mock(EventBus.class);

  @Before
  public void setUp() {
    readModelBuilder = new EventBasedMongoSecuritiesReadModelBuilder(db, eventBus);
    securitiesReadService = new MongoSecuritiesReadService(db, readModelBuilder);
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
