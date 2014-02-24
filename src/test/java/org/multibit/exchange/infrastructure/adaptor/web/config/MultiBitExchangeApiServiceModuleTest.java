package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.ReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.ExchangeResource;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.SecuritiesResource;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.infrastructure.service.AxonEventBasedExchangeService;
import org.multibit.exchange.service.ExchangeService;

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
    assertThat(testInjectee.getExchangeService()).isNotNull();
    assertThat(testInjectee.getExchangeService()).isInstanceOf(AxonEventBasedExchangeService.class);
    assertThat(testInjectee.getExchangeResource()).isNotNull();
    assertThat(testInjectee.getSecuritiesResource()).isNotNull();
    assertThat(testInjectee.getReadModelBuilder()).isNotNull();
    assertThat(testInjectee.getReadService()).isNotNull();
  }
}

class TestInjectee {

  @Inject
  @Singleton
  private ExchangeResource exchangeResource;

  @Inject
  @Singleton
  private SecuritiesResource securitiesResource;


  @Inject
  @Singleton
  private ExchangeService exchangeService;

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

  ExchangeService getExchangeService() {
    return exchangeService;
  }

  ReadService getReadService() {
    return readService;
  }

  public ExchangeResource getExchangeResource() {
    return exchangeResource;
  }

  public SecuritiesResource getSecuritiesResource() {
    return securitiesResource;
  }

  public ReadModelBuilder getReadModelBuilder() {
    return readModelBuilder;
  }
}
