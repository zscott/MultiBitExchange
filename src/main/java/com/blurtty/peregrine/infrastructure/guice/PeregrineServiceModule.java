package com.blurtty.peregrine.infrastructure.guice;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.events.GuavaEventPublisher;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadModelBuilder;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadService;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.service.DefaultApplicationService;
import com.blurtty.peregrine.service.EventPublisher;
import com.blurtty.peregrine.service.MarketReadService;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

import javax.inject.Inject;
import java.net.UnknownHostException;

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
    bind(EventPublisher.class).to(GuavaEventPublisher.class).asEagerSingleton();
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
  @Singleton
  public Mongo getMongo() {

    return getDB().getMongo();

  }

  @Provides
  @Singleton
  public DB getDB() {

    // MongoDB Setup
    final MongoURI mongoUri = new MongoURI(configuration.getMongoUri());
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
