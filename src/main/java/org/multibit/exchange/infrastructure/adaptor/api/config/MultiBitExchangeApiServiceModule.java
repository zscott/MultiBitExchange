package org.multibit.exchange.infrastructure.adaptor.api.config;

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
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventstore.EventStore;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.EventBasedMongoReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoReadService;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.ReadModelCollections;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Guice module to provide the following to application:</p>
 * <ul>
 * <li>Bindings between injected interfaces and their implementations</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MultiBitExchangeApiServiceModule extends AbstractModule {

  private static final Logger log = LoggerFactory.getLogger(MultiBitExchangeApiServiceModule.class);

  /**
   * The default locale. This ServiceModule is setup with bindings to
   * inject this wherever the annotation @DefaultLocale is used.
   *
   * @see org.multibit.exchange.infrastructure.web.BaseResource
   */
  public static final Locale DEFAULT_LOCALE = Locale.CANADA;

  // public static final String PATH_TO_EVENT_STORE_DIR = "./eventstore";

  private final MultiBitExchangeApiConfiguration configuration;

  public MultiBitExchangeApiServiceModule(MultiBitExchangeApiConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {

    // Event Store
    // Provider<EventStore>  eventStoreProvider = new FileSystemEventStoreProvider(PATH_TO_EVENT_STORE_DIR);
    bind(EventStore.class)
        .toProvider(MongoEventStoreProvider.class)
        .asEagerSingleton();

    // Event Bus
    bind(EventBus.class)
        .to(SimpleEventBus.class)
        .asEagerSingleton();

    // Command Bus
    bind(CommandBus.class)
        .toProvider(DisruptorCommandBusProvider.class)
        .asEagerSingleton();

    bind(DisruptorCommandBus.class)
        .toProvider(DisruptorCommandBusProvider.class)
        .asEagerSingleton();

    // Command Gateway
    bind(CommandGateway.class)
        .toProvider(DefaultCommandGatewayProvider.class)
        .asEagerSingleton();

    // ReadModel Builders
    bind(ReadModelBuilder.class)
        .to(EventBasedMongoReadModelBuilder.class)
        .asEagerSingleton();

    // Api Service
    bind(ExchangeService.class)
        .toProvider(EventBasedExchangeServiceProvider.class)
        .asEagerSingleton();

    // Read Services
    bind(ReadService.class)
        .to(MongoReadService.class)
        .asEagerSingleton();

    // Default Locale
    bind(Locale.class)
        .annotatedWith(DefaultLocale.class)
        .toInstance(DEFAULT_LOCALE);
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
      // todo - For testing - when there is no Uri create an embedded mongo instance - is there a better way to do this?
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
      // Check that collections can be reached anonymously instead
      db.getCollection(ReadModelCollections.SECURITIES).count();
      db.getCollection(ReadModelCollections.ORDERS).count();
    }
    return db;
  }

  private DB getSandboxDB() throws IOException {
    MongodForTestsFactory factory = new MongodForTestsFactory();
    Mongo mongo = factory.newMongo();
    return factory.newDB(mongo);
  }
}
