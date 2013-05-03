package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * <p>TestBase to provide the following to resource integration tests:</p>
 * <ul>
 * <li>Commonly used methods and setup</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ResourceIntegrationTestBase extends ResourceTest {

  protected final ExchangeService exchangeService = mock(ExchangeService.class);
  protected final ReadService readService = mock(ReadService.class);

  @Override
  protected void setUpResources() throws Exception {
    addResource(new SecuritiesResource(exchangeService, readService));
  }

  @After
  public void tearDown() {
    reset(readService);
    reset(exchangeService);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
