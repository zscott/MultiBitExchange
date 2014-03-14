package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoCurrencyPairReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQuoteReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.CurrencyPairsResource;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.ExchangeResource;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.infrastructure.service.AxonEventBasedExchangeService;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MultiBitExchangeApiServiceModuleTest {

  private Injector injector;

  @Before
  public void setUp() {
    // Arrange
    final MultiBitExchangeApiConfiguration configuration = mock(MultiBitExchangeApiConfiguration.class);
    MongoDBProvider provider = new TestMongoDBProvider();
    MultiBitExchangeApiServiceModule multiBitExchangeApiServiceModule
        = new MultiBitExchangeApiServiceModule(configuration, provider);
    injector = Guice.createInjector(multiBitExchangeApiServiceModule);
  }

  @Test
  public void testInjection() {
    // Arrange
    Locale expectedDefaultLocale = Locale.CANADA;

    // Act
    TestInjectee testInjectee = injector.getInstance(TestInjectee.class);

    // Assert
    assertThat(testInjectee.getDefaultLocale()).isEqualTo(expectedDefaultLocale);

    assertThat(testInjectee.getExchangeService()).isNotNull();
    assertThat(testInjectee.getExchangeService()).isInstanceOf(AxonEventBasedExchangeService.class);

    assertThat(testInjectee.getExchangeResource()).isNotNull();
    assertThat(testInjectee.getCurrencyPairsResource()).isNotNull();
    assertThat(testInjectee.getMongoCurrencyPairReadModelBuilder()).isNotNull();
    assertThat(testInjectee.getMongoQuoteReadModelBuilder()).isNotNull();
    assertThat(testInjectee.getReadService()).isNotNull();
  }
}

class TestInjectee {

  @Inject
  @Singleton
  private ExchangeResource exchangeResource;

  @Inject
  @Singleton
  private CurrencyPairsResource currencyPairsResource;


  @Inject
  @Singleton
  private ExchangeService exchangeService;

  @Inject
  @Singleton
  private QueryProcessor readService;

  @Inject
  private MongoCurrencyPairReadModelBuilder mongoCurrencyPairReadModelBuilder;

  @Inject
  private MongoQuoteReadModelBuilder mongoQuoteReadModelBuilder;

  @Inject
  @DefaultLocale
  private Locale defaultLocale;

  Locale getDefaultLocale() {
    return defaultLocale;
  }

  ExchangeService getExchangeService() {
    return exchangeService;
  }

  QueryProcessor getReadService() {
    return readService;
  }

  public ExchangeResource getExchangeResource() {
    return exchangeResource;
  }

  public CurrencyPairsResource getCurrencyPairsResource() {
    return currencyPairsResource;
  }

  public MongoCurrencyPairReadModelBuilder getMongoCurrencyPairReadModelBuilder() {
    return mongoCurrencyPairReadModelBuilder;
  }

  public MongoQuoteReadModelBuilder getMongoQuoteReadModelBuilder() {
    return mongoQuoteReadModelBuilder;
  }
}
