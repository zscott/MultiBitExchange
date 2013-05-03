package org.multibit.exchange.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.api.config.MultiBitExchangeApiConfiguration;
import org.multibit.exchange.infrastructure.adaptor.api.config.MultiBitExchangeApiServiceModule;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.service.MarketService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MultiBitExchangeServiceModuleTest {

  private Injector injector;

  @Before
  public void setUp() {
    // Arrange
    final MultiBitExchangeApiConfiguration configuration = mock(MultiBitExchangeApiConfiguration.class);
    injector = Guice.createInjector(new MultiBitExchangeApiServiceModule(configuration));
  }

  @Test
  public void testInjection() {
    // Arrange
    Locale expectedDefaultLocale = Locale.CANADA;

    // Act
    TestInjectee testInjectee = injector.getInstance(TestInjectee.class);

    // Assert
    assertThat(testInjectee.getDefaultLocale()).isEqualTo(expectedDefaultLocale);
    assertThat(testInjectee.getMarketService()).isNotNull();
    assertThat(testInjectee.getReadService()).isNotNull();
  }
}

class TestInjectee {

  @Inject
  @Singleton
  private MarketService marketService;

  @Inject
  @Singleton
  private ReadService readService;

  @Inject
  @Singleton
  private ReadModelBuilder readModelBuilder;

  @Inject
  @DefaultLocale
  private Locale defaultLocale;

  Locale getDefaultLocale() {
    return defaultLocale;
  }

  MarketService getMarketService() {
    return marketService;
  }

  ReadService getReadService() {
    return readService;
  }
}
