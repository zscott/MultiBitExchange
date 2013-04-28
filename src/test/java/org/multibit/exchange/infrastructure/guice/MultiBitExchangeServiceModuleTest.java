package org.multibit.exchange.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Locale;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.api.config.MultiBitExchangeConfiguration;
import org.multibit.exchange.infrastructure.adaptor.api.config.MultiBitExchangeServiceModule;
import org.multibit.exchange.infrastructure.guice.annotation.DefaultLocale;
import org.multibit.exchange.service.ApiService;
import org.multibit.exchange.service.SecuritiesReadService;


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
  public void testInjection() {
    // Arrange
    Locale expectedDefaultLocale = Locale.CANADA;

    // Act
    TestInjectee testInjectee = injector.getInstance(TestInjectee.class);

    // Assert
    assertThat(testInjectee.getDefaultLocale()).isEqualTo(expectedDefaultLocale);
    assertThat(testInjectee.getApiService()).isNotNull();
    assertThat(testInjectee.getSecuritiesReadService()).isNotNull();
  }
}

class TestInjectee {

  private final ApiService apiService;

  private final SecuritiesReadService securitiesReadService;

  @Inject
  @DefaultLocale
  private Locale defaultLocale;

  @Inject
  TestInjectee(ApiService apiService, SecuritiesReadService securitiesReadService) {
    this.apiService = apiService;
    this.securitiesReadService = securitiesReadService;
  }

  Locale getDefaultLocale() {
    return defaultLocale;
  }

  ApiService getApiService() {
    return apiService;
  }

  SecuritiesReadService getSecuritiesReadService() {
    return securitiesReadService;
  }
}
