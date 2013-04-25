package com.blurtty.peregrine.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.events.disruptor.DisruptorMarketEventPublisherInjectionProvider;
import com.blurtty.peregrine.infrastructure.guice.annotation.DefaultLocale;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadModelBuilder;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoMarketReadService;
import com.blurtty.peregrine.infrastructure.service.DefaultMarketService;

import com.blurtty.peregrine.service.MarketReadService;
import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.domain.market.MarketEventPublisher;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;

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

  /**
   * The default locale. This ServiceModule is setup with bindings to
   * inject this wherever the annotation @DefaultLocale is used.
   *
   * @see com.blurtty.peregrine.infrastructure.dropwizard.common.BaseResource
   */
  public static final Locale DEFAULT_LOCALE = Locale.CANADA;

  private final PeregrineConfiguration configuration;

  public PeregrineServiceModule(PeregrineConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {

    // Application support
    bind(MarketService.class).to(DefaultMarketService.class).asEagerSingleton();

    // Read Services
    bind(MarketReadService.class).to(MongoMarketReadService.class).asEagerSingleton();

    // Read Model Builders
    bind(MarketReadModelBuilder.class).to(MongoMarketReadModelBuilder.class).asEagerSingleton();

    // Default Locale
    bind(Locale.class).annotatedWith(DefaultLocale.class).toInstance(DEFAULT_LOCALE);

    // Event Publisher
    bind(MarketEventPublisher.class).toProvider(DisruptorMarketEventPublisherInjectionProvider.class);
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
    if (mongoUriString == null) {
      // todo - is there a better way to do this?
      try {
        return getSandboxDB();
      } catch (IOException e) {
        throw new RuntimeException("Unable to spin up a mongo sandbox.", e);
      }
    }

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
      db.getCollection("market").count();
    }
    return db;
  }

  private DB getSandboxDB() throws IOException {
    MongodForTestsFactory factory = new MongodForTestsFactory();
    Mongo mongo = factory.newMongo();
    return factory.newDB(mongo);
  }

}
