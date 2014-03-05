package org.multibit.exchange.infrastructure.adaptor.web.integration;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.ExchangeResource;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.SecuritiesResource;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.service.QueryProcessor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * <p>Base test class to provide the following to dropwizard resource integration tests:</p>
 * <ul>
 * <li>Commonly used methods and setup</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class BaseDropWizardResourceIntegrationTest extends ResourceTest {

  protected final ExchangeService exchangeService = mock(ExchangeService.class);
  protected final QueryProcessor readService = mock(QueryProcessor.class);

  @Override
  protected void setUpResources() throws Exception {
    addResource(new SecuritiesResource(exchangeService, readService));
    addResource(new ExchangeResource(exchangeService, readService));
  }

  @After
  public void tearDown() {
    reset(readService);
    reset(exchangeService);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
