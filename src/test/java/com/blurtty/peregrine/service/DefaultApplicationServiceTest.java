package com.blurtty.peregrine.service;

import com.blurtty.peregrine.domain.MarketEventPublisherService;
import com.blurtty.peregrine.infrastructure.service.DefaultApplicationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

/**
 * <p>DefaultApplicationServiceTest provides the following to service tests:</p>
 * <ul>
 *   <li>an initialized instance of an MarketService</li>
 *   <li>an initialized instance of an EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class DefaultApplicationServiceTest {

  protected MarketService appService;
  private MarketEventPublisherService marketEventPublisher;

  @Before
  public void setUp() {
    marketEventPublisher = mock(MarketEventPublisherService.class);
    appService = new DefaultApplicationService(marketEventPublisher);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
