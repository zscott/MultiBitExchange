package com.blurtty.peregrine.infrastructure.guice;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.service.ApplicationService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

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
    injector.getProvider(ApplicationService.class);
  }
}
