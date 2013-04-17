package com.blurtty.peregrine.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.guice.annotation.DefaultLocale;

import com.blurtty.peregrine.service.MarketReadService;
import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.domain.MarketEventPublisherService;
import com.blurtty.peregrine.readmodel.MarketReadModelBuilder;


import javax.inject.Inject;
import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PeregrineServiceModuleTest {

  private Injector injector;

  @Before
  public void setUp() {
    // Arrange
    final PeregrineConfiguration configuration = mock(PeregrineConfiguration.class);
    injector = Guice.createInjector(new PeregrineServiceModule(configuration));
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
    injector.getProvider(MarketEventPublisherService.class);
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
