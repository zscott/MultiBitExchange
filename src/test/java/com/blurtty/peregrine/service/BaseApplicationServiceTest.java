package com.blurtty.peregrine.service;

import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

/**
 * <p>BaseApplicationServiceTest provides the following to service tests:</p>
 * <ul>
 *   <li>an initialized instance of an ApplicationService</li>
 *   <li>an initialized instance of an EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class BaseApplicationServiceTest {

  protected ApplicationService appService;
  private EventPublisher eventPublisher;

  @Before
  public void setUp() {
    eventPublisher = mock(EventPublisher.class);
    appService = new DefaultApplicationService(eventPublisher);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
