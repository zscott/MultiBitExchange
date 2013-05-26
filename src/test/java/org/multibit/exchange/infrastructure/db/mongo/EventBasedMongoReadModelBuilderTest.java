package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.eventhandling.EventBus;
import org.junit.After;
import org.junit.Before;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.EventBasedMongoReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoReadService;


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

}
