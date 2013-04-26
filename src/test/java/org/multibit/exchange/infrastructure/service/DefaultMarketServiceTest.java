package org.multibit.exchange.infrastructure.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.event.MarketEventTopic;


import static org.mockito.Mockito.mock;

/**
 * <p>DefaultMarketServiceTest provides the following to service tests:</p>
 * <ul>
 *   <li>an initialized instance of an MarketService</li>
 *   <li>an initialized instance of an EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class DefaultMarketServiceTest {

  protected DefaultMarketService marketService;

  @Before
  public void setUp() {
    MarketEventTopic marketEventTopic = mock(MarketEventTopic.class);
    marketService = new DefaultMarketService(marketEventTopic);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
