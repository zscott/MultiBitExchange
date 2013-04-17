package com.blurtty.peregrine.infrastructure.guice;

import com.blurtty.peregrine.domain.MarketEvent;
import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadModelBuilder;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadService;
import com.blurtty.peregrine.infrastructure.service.DefaultApplicationService;
import com.blurtty.peregrine.infrastructure.service.DisruptorEvent;
import com.blurtty.peregrine.infrastructure.service.DisruptorEventFactory;
import com.blurtty.peregrine.infrastructure.service.DisruptorEventPublisher;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.service.EventPublisher;
import com.blurtty.peregrine.service.MarketReadService;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

/**
 * <p>Guice module to provide the following to application:</p>
 * <ul>
 * <li>Bindings between injected interfaces and their implementations</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class PeregrineServiceModule extends AbstractModule {

  private static final Logger log = LoggerFactory.getLogger(PeregrineServiceModule.class);

  public static final int MARKET_ADDED_DISRUPTOR_RING_SIZE = 1024;

  public static final WaitStrategy MARKET_ADDED_DISRUPTOR_WAIT_STRATEGY = new SleepingWaitStrategy();

  private final PeregrineConfiguration configuration;

  public PeregrineServiceModule(PeregrineConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {

    // Application support
    bind(ApplicationService.class).to(DefaultApplicationService.class).asEagerSingleton();

    // Read Services
    bind(MarketReadService.class).to(MongoMarketReadService.class).asEagerSingleton();

    // Read Model Builders
    bind(MarketReadModelBuilder.class).to(MongoMarketReadModelBuilder.class).asEagerSingleton();

    // Event Publisher
    //bind(EventPublisher.class).to(GuavaEventPublisher.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  @Inject
  public EventBus getEventBus(MarketReadModelBuilder marketReadModelBuilder) {
    EventBus eventBus = new EventBus();
    eventBus.register(marketReadModelBuilder);
    return eventBus;
  }

  @Provides
  @Named("MarketEventPublisher")
  @Singleton
  @Inject
  public EventPublisher<MarketEvent> getDisruptorEventPublisher(final MarketReadModelBuilder marketReadModelBuilder) {
    final Disruptor<DisruptorEvent<MarketEvent>> disruptor = new Disruptor<DisruptorEvent<MarketEvent>>(
        new DisruptorEventFactory<MarketEvent>(),
        MARKET_ADDED_DISRUPTOR_RING_SIZE,
        Executors.newFixedThreadPool(2),
        ProducerType.MULTI,
        MARKET_ADDED_DISRUPTOR_WAIT_STRATEGY);

    disruptor.handleEventsWith(new EventHandler<DisruptorEvent<MarketEvent>>() {
      @Override
      public void onEvent(DisruptorEvent<MarketEvent> event, long sequence, boolean endOfBatch) throws Exception {
        marketReadModelBuilder.handleMarketEvent(event.getEventPayload());
      }
    });

    disruptor.start();

    return new DisruptorEventPublisher<MarketEvent>(disruptor);
  }

  @Provides
  @Singleton
  public Mongo getMongo() {
    return getDB().getMongo();
  }

  @Provides
  @Singleton
  public DB getDB() {
    // MongoDB Setup
    String mongoUriString = configuration.getMongoUri();
    log.error("mongoUri=%s", mongoUriString);

    final MongoURI mongoUri = new MongoURI(mongoUriString);
    final DB db;
    try {
      db = mongoUri.connectDB();
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("Cannot connect to MongoDB", e);
    }

    // Authenticate
    if (mongoUri.getUsername() != null && mongoUri.getPassword() != null) {
      db.authenticate(mongoUri.getUsername(), mongoUri.getPassword());
    } else {
      // Check that a collection can be reached anonymously instead
      db.getCollection("orders").count();
    }
    return db;
  }
}
