package com.blurtty.peregrine.infrastructure.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.domain.MarketEventPublisherService;


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

  protected MarketService appService;
  private MarketEventPublisherService marketEventPublisher;

  @Before
  public void setUp() {
    marketEventPublisher = mock(MarketEventPublisherService.class);
    appService = new DefaultMarketService(marketEventPublisher);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
