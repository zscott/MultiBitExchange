package com.blurtty.peregrine.testing.helper;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.guice.PeregrineServiceModule;
import com.blurtty.peregrine.service.ApplicationService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.mockito.Mockito.mock;

/**
 * <p>TestHelper to provide the following to tests:</p>
 * <ul>
 * <li>Guice bindings</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class GuiceTestHelper {

  private static final PeregrineConfiguration configuration;
  private static final Injector injector;

  static {
    configuration = mock(PeregrineConfiguration.class);
    injector = Guice.createInjector(new PeregrineServiceModule(configuration));
  }

  public static ApplicationService getApplicationService() {
    return injector.getProvider(ApplicationService.class).get();
  }
}
