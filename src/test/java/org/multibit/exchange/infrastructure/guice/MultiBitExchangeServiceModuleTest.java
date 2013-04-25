package org.multibit.exchange.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Locale;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import org.multibit.exchange.infrastructure.dropwizard.MultiBitExchangeConfiguration;
import org.multibit.exchange.infrastructure.guice.annotation.DefaultLocale;

import org.multibit.exchange.service.MarketReadService;
import org.multibit.exchange.service.MarketService;

import org.multibit.exchange.domain.market.MarketEventPublisher;
import org.multibit.exchange.readmodel.MarketReadModelBuilder;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MultiBitExchangeServiceModuleTest {

  private Injector injector;

  @Before
  public void setUp() {
    // Arrange
    final MultiBitExchangeConfiguration configuration = mock(MultiBitExchangeConfiguration.class);
    injector = Guice.createInjector(new MultiBitExchangeServiceModule(configuration));
  }

  @Test
  public void testApplicationServiceBinding() {
    injector.getProvider(MarketService.class);
  }

  @Test
  public void testMarketReadModelBuilderBinding() {
    injector.getProvider(MarketReadModelBuilder.class);
  }

  @Test
  public void testMarketReadServiceBinding() {
    injector.getProvider(MarketReadService.class);
  }

  @Test
  public void testMarketEventPublisherServiceBinding() {
    injector.getProvider(MarketEventPublisher.class);
  }

  @Test
  public void testDefaultLocaleBinding() {
    // Arrange
    Locale expectedDefaultLocale = Locale.CANADA;

    // Act
    RequiresDefaultLocale requiresDefaultLocale = injector.getInstance(RequiresDefaultLocale.class);

    // Assert
    assertThat(requiresDefaultLocale.getDefaultLocale()).isEqualTo(expectedDefaultLocale);
  }
}

class RequiresDefaultLocale {

  @Inject
  @DefaultLocale
  private Locale defaultLocale;


  Locale getDefaultLocale() {
    return defaultLocale;
  }
}
