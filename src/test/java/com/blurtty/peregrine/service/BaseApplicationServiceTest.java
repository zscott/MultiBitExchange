package com.blurtty.peregrine.service;

import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

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

  protected EventBus eventBus;
  protected ApplicationService appService;

  @Before
  public void setUp() {
    eventBus = new EventBus();
    appService = new DefaultApplicationService(eventBus);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
