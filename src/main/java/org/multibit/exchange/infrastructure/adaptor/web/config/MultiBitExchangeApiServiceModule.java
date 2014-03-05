package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.DB;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.disruptor.DisruptorCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventstore.EventStore;
import org.multibit.exchange.infrastructure.adaptor.atmosphere.TradeStream;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoCurrencyPairReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQueryProcessor;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQuoteReadModelBuilder;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.infrastructure.service.AxonEventBasedExchangeService;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import java.util.Locale;

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

  /**
   * The default locale. This ServiceModule is setup with bindings to
   * inject this wherever the annotation @DefaultLocale is used.
   *
   * @see org.multibit.exchange.infrastructure.web.BaseResource
   */
  public static final Locale DEFAULT_LOCALE = Locale.CANADA;

  // public static final String PATH_TO_EVENT_STORE_DIR = "./eventstore";

  private final MultiBitExchangeApiConfiguration configuration;

  private MongoDBProvider mongoDBProvider;

  public MultiBitExchangeApiServiceModule(MultiBitExchangeApiConfiguration configuration) {
    this.configuration = configuration;
    this.mongoDBProvider = new ProductionMongoDBProvider(configuration);
  }

  public MultiBitExchangeApiServiceModule(MultiBitExchangeApiConfiguration configuration, MongoDBProvider mongoDBProvider) {
    this.configuration = configuration;
    this.mongoDBProvider = mongoDBProvider;
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
    bind(MongoCurrencyPairReadModelBuilder.class)
            .asEagerSingleton();

    bind(MongoQuoteReadModelBuilder.class)
            .asEagerSingleton();


    // Stream Broadcasters
    bind(TradeStream.class).asEagerSingleton();

    // Api Service
    bind(ExchangeService.class)
        .to(AxonEventBasedExchangeService.class)
        .asEagerSingleton();

    // Read Services
    bind(QueryProcessor.class)
            .to(MongoQueryProcessor.class)
            .asEagerSingleton();

    // Default Locale
    bind(Locale.class)
        .annotatedWith(DefaultLocale.class)
        .toInstance(DEFAULT_LOCALE);
  }

  @Provides
  @Singleton
  @SuppressWarnings("unused")
  public DB getMongoDB() {
    return mongoDBProvider.get();
  }
}
